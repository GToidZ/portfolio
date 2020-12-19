require('dotenv').config();

//console.log(`discord bot token = ${process.env.DISCORD_BOT_TOKEN}`);
//console.log(`saucenao api key = ${process.env.SAUCENAO_API_KEY}`);
console.log(`discord bot invite = https://discord.com/api/oauth2/authorize?client_id=${process.env.DISCORD_BOT_CLIENT}&scope=bot&permissions=${process.env.DISCORD_BOT_PERMS}`);

const IMG_FILE_EXTS = ['.jpg', '.jpeg', '.bmp', '.png', '.gif'];
const talkedRecently = new Set();

const path = require('path');
const https = require("https");
const { Client, MessageEmbed } = require('discord.js');

const client = new Client();
client.login(process.env.DISCORD_BOT_TOKEN);

client.on('ready', () => {
    console.log(`Sauce-chan says Ohayou!`);
    client.user.setActivity('sauce', {type:"LISTENING"});
});

findSauce = async (dbquery, target) => {
    const url = `https://saucenao.com/search.php?db=${dbquery}&output_type=2&api_key=${process.env.SAUCENAO_API_KEY}&testmode=1&numres=1&url=${target}`;
    https.get(url, resp => {
        var data = '';
        resp.on("data", chunk => {
            data += chunk;
        });
        resp.on("end", () => {
            var json = JSON.parse(data);
            return json;
        });
    }).on("error", err => {
        console.log(err.message);
    });
}

client.on('message', async (message) => {
    if(message.content.substr(0, 5) === 'sauce'){
        if(message.content.includes('.', 5)){
            context = message.content.split('.');
            //console.log(context);
            if(context[1] === 'ping'){
                message.channel.send(`Hey, I'm still alive!`)
            }
        } else {
            if(message.attachments.size > 0){
                if(talkedRecently.has(message.author.id)){
                    message.reply(`Ah, you need to wait for a bit more to use me!`)
                } else {
                    if(message.attachments.size > 1) {
                        message.reply(`Ah, I can't work with a lot of images! Please send one at a time!`);
                        return;
                    }
                    message.attachments.forEach(async(value) => {
                        const target = value.url;
                        const foo = path.parse(target);
                        if(IMG_FILE_EXTS.includes(foo.ext)){
                            //console.log(`${target}`)
                            if(message.channel.nsfw){
                                try {
                                    var jsonData = await findSauce('999', target);
                                    console.log(jsonData.results)
                                    message.channel.send(
                                        new MessageEmbed()
                                        .setTitle(`Click Me!`)
                                        .setURL(jsonData.results[0].data.ext_urls[0])
                                        .setAuthor(`Sauce-chan tries her best...`)
                                        .setImage(jsonData.results[0].header.thumbnail)
                                        .setFooter(`Remaining Searches (24h): ${jsonData.header.long_remaining}`))
                                } catch (err) {
                                    console.log(err);
                                }
                            } else {

                            }
                            talkedRecently.add(message.author.id);
                            setTimeout(() => {
                                talkedRecently.delete(message.author.id);
                            }, 10000);
                        } else {
                            message.reply(`Ah, I can only work with JPG, JPEG, PNG, BMP and GIF!`)
                        }
                    });
                }
            }
        }
    }
});

/*message.channel.send(
    new MessageEmbed()
    .setTitle(`Click Me!`)
    .setURL(jsonData.results[0].data.ext_urls[0])
    .setAuthor(`Sauce-chan tries her best...`)
    .setImage(jsonData.results[0].header.thumbnail)
    .setFooter(`Remaining Searches (24h): ${jsonData.header.long_remaining}`))*/