
#include <SoftwareSerial.h>       // 블루투스 통신을 위한 라이브러
#include <Wire.h>                 // I2C 통신을 위한 라이브러리 
#include <LiquidCrystal_I2C.h>    // LCD 1602 I2C용 라이브러리
#include <Adafruit_NeoPixel.h>                // RGB_LED 사용을 위한 라이브러리 선언

#define Tx 7          // 블루투스 송신
#define Rx 8          // 블루투스 수신
#define led_foot 6    // 원형 LED
#define led_R 13      // 좌석 사용 알림 LED
#define led_G 12      // 좌석 사용 알림 LED
#define led_B 11      // 좌석 사용 알림 LED

SoftwareSerial bluetooth(Tx, Rx);     // 블루투스 통신
LiquidCrystal_I2C lcd(0x27, 16, 2);   // LCD 접근 주소 : 0x3F or 0x27
Adafruit_NeoPixel circle = Adafruit_NeoPixel(12, led_foot, NEO_GRB); // 8개의 LED와 제어핀 설정 

void setup() {
  Serial.begin(9600);
  bluetooth.begin(9600);

  lcd.init();
  lcd.backlight();

  circle.begin();
  circle.show();

  pinMode(led_foot, OUTPUT);
  pinMode(led_R, OUTPUT);
  pinMode(led_G, OUTPUT);
  pinMode(led_B, OUTPUT);
}

void loop() {
    if (bluetooth.available()) {
    char value = bluetooth.read();

    Serial.println(value);

    // on 버튼 눌렀을 때
    if (value == '1') {
      LEDControl(0, 255, 0);
      LCDControl(1);   // 자리 비워주세요 < 표시
    }

    // off 버튼 눌렀을 때
    else if (value == '0') {
      LEDControl(0, 0, 0);
      LCDControl(0);   // 사용중 < 표시
    }

    delay(180000);
  } 
}

void LEDControl(int r, int g, int b) {
  // 머리 위의 LED 제어
  analogWrite(led_R, r);
  analogWrite(led_G, g);
  analogWrite(led_B, b);

  colorWipe(circle.Color(255, 0, 0), 50);

  // 발 밑의 LED 제어
  //for (int i = 0; i < 12; i++) {
  //  circle.setPixelColor(i, 255, 0, 0);
  //  circle.show();
  //}
}

void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<circle.numPixels(); i++) {
      circle.setPixelColor(i, c);
      circle.show();
      delay(wait);
  }
}


void LCDControl(int i) {
  if (i == 1) // on
  {
    lcd.setCursor(0, 0);
    lcd.print("give your seat");
    lcd.setCursor(0, 1);
    lcd.print("for pregnant woman.");
  }

  else { // off
    lcd.init();
  }
}
