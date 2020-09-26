
#include <SoftwareSerial.h>       // 블루투스 통신을 위한 라이브러
#include <Wire.h>                 // I2C 통신을 위한 라이브러리 
#include <LiquidCrystal_I2C.h>    // LCD 1602 I2C용 라이브러리
#include <Adafruit_NeoPixel.h>    // RGB_LED 사용을 위한 라이브러리 선언

#define Tx 7          // 블루투스 송신
#define Rx 8          // 블루투스 수신
#define led_foot 6    // 원형 LED
#define led_R 13      // 좌석 사용 알림 LED
#define led_G 12      // 좌석 사용 알림 LED
#define led_B 11      // 좌석 사용 알림 LED

SoftwareSerial bluetooth(Rx, Tx);     // 블루투스 통신
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

  // 머리 위의 LED 파란색으로 초기화
  LEDControl(HIGH, LOW, LOW);
}

void loop() {
  if (bluetooth.available()) {
    char value = bluetooth.read();

    Serial.println(value);

    // on 버튼 눌렀을 때
    if (value == '1') {
      LEDControl(LOW, HIGH, LOW); // 분홍색으로 전환 
      LCDControl(1);
      colorWipe(circle.Color(255, 0, 0), 500);
    }

    // off 버튼 눌렀을 때
    else if (value == '0') {
      LEDControl(HIGH, LOW, LOW); // 파란색으로 전환 
      LCDControl(0);
      circle.fill((0, 0, 0));
      circle.show();
    }
  }
}

// 머리 위의 LED 제어 
void LEDControl(int r, int g, int b) {
  digitalWrite(led_R, r);
  digitalWrite(led_G, g);
  digitalWrite(led_B, b);
}

// 발판 LED 제어 
void colorWipe(uint32_t c, uint8_t wait) {
  for (uint16_t i = 0; i < circle.numPixels(); i++) {
    circle.setPixelColor(i, c);
    circle.show();
    delay(wait);
  }
}

// LCD 제어 
void LCDControl(int i) {
  // on
  if (i == 1)
  {
    lcd.setCursor(0, 0);
    lcd.print("give your seat");
    lcd.setCursor(0, 1);
    lcd.print("for pregnant woman.");
  }

  // off
  else {
    lcd.init();
  }
}
