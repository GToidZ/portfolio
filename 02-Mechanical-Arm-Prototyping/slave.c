#include<string.h>
#include<Servo.h>

Servo base, low_a, low_b, up, rota, widen;
int config[6] = {9,21,18,10,6,4};
int nbase, nlow_a, nlow_b, nup, nrota, nwiden;
void setup(){
    Serial.begin(9600);
	if(!Serial.available()){
		
	}	
	setupServos(config);
}

void loop(){
	if(Serial.available()){
		parse(Serial.readString());
	}
}

void parse(char *message){
	char cmd[4];
	char args[512];
	memcpy(cmd, message, 3);
	memcpy(args, message, sizeof(args));
	cmd[3] = '\0';
	memmove(args, args+4, sizeof(args));
	switch(cmd){
		case "UPL":
			cmdUpload();
			break;
		case "ROT":
			cmdRotate(args);
			break;
		case "ACT":
			cmdAction(args);
			break;
        default:
            Serial.println("Unknown command, please refer to the manual [\"CORTA_REFERENCE_GUIDE.pdf\"]);
	}
}

void cmdUpload(){
	setAllMotorsTo(90);
}

void cmdRotate(char args){
	char *a;
    a = strtok(args, ",");
    while(a != NULL){
        char *motor = strtok(a, ":");
        char *operation = strtok(NULL, ":");
        int op = atoi(operation);
        switch(motor){
            case "BASE":
                if(op>0){
                    nbase += 1;
                    base.write(nbase);
                }
                else {
                    nbase -= 1;
                    base.write(nbase);
                }
            break;
            case "LOW":
                if(op>0){
                    nlow_a += 1;
                    nlow_b -= 1;
                    low_a.write(nlow_a);
                    low_b.write(nlow_b);
                }
                else {
                    nlow_a -= 1;
                    nlow_b += 1;
                    low_a.write(nlow_a);
                    low_b.write(nlow_b);
                }
            break;
            case "UP":
                if(op>0){
                    nup += 1;
                    up.write(nup);
                }
                else {
                    nup -= 1;
                    up.write(nup);
                }
            break;
            case "JAWR":
                if(op>0){
                    nrota += 1;
                    rota.write(nrota);
                }
                else {
                    nrota -= 1;
                    rota.write(nrota);
                }
            break;
            case "JAWW":
                if(op>0){
                    nwiden += 1;
                    widen.write(nwiden);
                }
                else {
                    nwiden -= 1;
                    widen.write(nwiden);
                }
            break;
        }
        a = strtok(NULL, ",");
    }
}

void cmdAction(char args){
    
}

void setupServos(int pins[]){
    base.attach(pins[0]);
    low_a.attach(pins[1]);
    low_b.attach(pins[2]);
    up.attach(pins[3]);
    rota.attach(pins[4]);
    widen.attach(pins[5]);
}

void setAllMotorsTo(int i){
    nbase = i;
    nlow_a = i;
    nlow_b = i;
    nup = i;
    nrota = i;
    nwiden = i;
    
    base.write(nbase);
    low_a.write(nlow_a);
    low_b.write(nlow_b);
    up.write(nup);
    rota.write(nrota);
    widen.write(nwiden);
}