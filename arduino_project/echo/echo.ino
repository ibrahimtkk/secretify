/* I2C haberleşmesinde Master olarak görev yapan Arduino kodu */

 
int deger = 0;
#include <Wire.h>
/* 
 * I2C fonksiyonlarını kullanabilmek için 
 * Wire.h kütüphanesini projemize ekledik
 */
 
void setup()
{
  Wire.begin();
  /* I2C haberleşmesi master olarak başlatıldı */
  randomSeed(analogRead(0));
  Serial.begin(9600);


  Serial.println("AA AAAA nerelee geldik boleee");
  /* Bilgisayara veri yazdırabilmek için seri haberleşme başlatıldı */

}



String generateValues(int deviceid)
{
  float usage_raw = random(1000)/30.0f;
  float freeusage = random(1000)/10.0f;
  float usage = freeusage+ usage_raw;
  String s = "";
  s.concat(deviceid); s.concat(',');
  s.concat(usage); s.concat(',');
  s.concat(freeusage); s.concat(';');
  return s;
}


int ms;

void loop()
{


  int cur = millis();
  if(cur - ms > 1000)
  {
              String yazbunu = "eved ";
              yazbunu += cur;
               Serial.println(yazbunu); 
               ms = cur;
  }

if( deger > 1000 || true){ // bypas

      if(isMessageAvailable())
      {
        auto msg = getMessage();

        if(msg.startsWith("getValues"))
        {
           auto s0 = generateValues(0);
           auto s1 = WireReadFrom(1 , 32);
           s0.concat(s1);
           Serial.println(s0.c_str()); 
        }else
        {
          Serial.print("unhandled message:");
          Serial.print(msg.c_str());
          if(msg[msg.length()-1] != '\n')
            Serial.println();
        }
      }
  }
}




String WireReadFrom(int targetid , int byte_count)
{
      String s = "";
      Wire.requestFrom(targetid, byte_count);
      while(Wire.available())    // slave may send less than requested
      {
      char c = Wire.read();    // receive a byte as character
      s.concat(c);
      }
      return s;
}



String bufferString = "";
String inputString = "";
bool available_message= false;
bool isMessageAvailable()
{
  if(available_message)
  {
    available_message = false;
    return true;
  }else
    return false;
}

String getMessage()
{
  inputString = String(bufferString);
  bufferString = "";
  
  
  return inputString;
}



void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read();
    // add it to the inputString:
    bufferString += inChar;
    // if the incoming character is a newline, set a flag so the main loop can
    // do something about it:
    if (inChar == '\n') {
      available_message = true;
    }
  }
}
