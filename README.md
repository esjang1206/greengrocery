# 청과물 중계 API 명세서

## index 페이지

```http
GET /index
```

#### Response    
- **Code** 200
- **HEADER**    
  ```
  Set-cookie : fruitAccessToken=${fruitAccessToken}
  Set-cookie : vegetableAccessToken=${vegetableAccessToken}
  ```


## 과일 목록 조회

```http
GET /fruit/list
```

#### Request    
- **HEADER**    
  ```
  Cookie: fruitAccessToken=${fruitAccessToken};
  ```

#### Response    
- **Code**  200
- **Content**    
  ```
  ["배","토마토","사과","바나나"]
  ```

## 과일 가격 조회

```http
GET /fruit/price
```

#### Request    
- **Data Params** `{ name : "사과" }` 
- **HEADER**    
  ```
  Cookie: fruitAccessToken=${fruitAccessToken};
  ```
#### Response    
- **Code**  200
- **Content**    
  ```json
  {"success":true,"errorCode":0,"data":"{\"name\":\"사과\",\"price\":1500}"}
  ```


## 채소 목록 조회

```http
GET /vegetable/list
```

#### Request    
- **HEADER**    
  ```
  Cookie: vegetableAccessToken=${vegetableAccessToken};
  ```

#### Response    
- **Code**  200
- **Content**    
  ```
  ["치커리","토마토","깻잎","상추"]
  ```

## 채소 가격 조회

```http
GET /vegetable/price
```

#### Request    
- **Data Params** `{ name : "상추" }` 
- **HEADER**    
  ```
  Cookie: vegetableAccessToken=${vegetableAccessToken};
  ```
#### Response    
- **Code**
    200
- **Content**    
  ```json
  {"success":true,"errorCode":0,"data":"{\"name\":\"상추\",\"price\":1400}"}
  ```
# 결과 


# 구성

```bash
├─main
│  ├─java
│  │  └─com
│  │      └─project
│  │          └─greengrocery
│  │              │  GreengroceryApplication.java
│  │              │  ServletInitializer.java
│  │              │
│  │              ├─controller
│  │              │      FruitController.java
│  │              │      GreengroceryController.java
│  │              │      VegetableController.java
│  │              │
│  │              ├─model
│  │              │      AjaxModel.java
│  │              │      Category.java
│  │              │      GreenGroceryErrorCode.java
│  │              │
│  │              ├─service
│  │              │      GreengroceryService.java
│  │              │
│  │              └─util
│  │                      APIHttpRequest.java
│  │
│  ├─resources
│  │  │  application.properties
│  │  │
│  │  ├─static
│  │  │  └─js
│  │  │          index.js
│  │  │
│  │  └─templates
│  └─webapp
│      └─WEB-INF
│          └─views
│                  index.jsp
│
└─test
    └─java
        └─com
            └─project
                └─greengrocery
                    │  GreengroceryApplicationTests.java
                    │
                    ├─controller
                    │      FruitControllerTest.java
                    │      GreengroceryControllerTest.java
                    │      VegetableControllerTest.java
                    │
                    └─service
                            GreengroceryServiceTest.java

``` 
