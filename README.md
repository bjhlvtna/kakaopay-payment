# 카카오페이 사전과제 2 - 결제 시스템 API 개발

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate
    - OS: Mac OS X
    - GIT
- Server
    - Java8
    - Spring Boot 2.2.6
    - JPA
    - H2
    - maven 

## 빌드 및 실행하기
### 터미널 환경
```
$ git clone https://github.com/bjhlvtna/kakaopay-payment.git 
$ cd kakaopay-payment
$ mvn clean package
$ java -jar target/payment-0.0.1-SNAPSHOT.jar ( or mvn clean spring-boot:run)
```

## API 명세서 
URI scheme
----------

*Host* : localhost:8080 *BasePath* : /payments

Tags
----

-   payment-controller : Payment Controller

Paths
=====

결제 API
--------

    POST /api/v1/payments

### Parameters

<table>
<colgroup>
<col style="width: 11%" />
<col style="width: 16%" />
<col style="width: 50%" />
<col style="width: 22%" />
</colgroup>
<thead>
<tr class="header">
<th>Type</th>
<th>Name</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>Body</strong></p></td>
<td><p><strong>paymentReq</strong><br />
<em>required</em></p></td>
<td><p>paymentReq</p></td>
<td><p><a href="#_paymentreq">PaymentReq</a></p></td>
</tr>
</tbody>
</table>

### Responses

<table>
<colgroup>
<col style="width: 10%" />
<col style="width: 70%" />
<col style="width: 20%" />
</colgroup>
<thead>
<tr class="header">
<th>HTTP Code</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>201</strong></p></td>
<td><p>Created</p></td>
<td><p><a href="#_transactionres">TransactionRes</a></p></td>
</tr>
</tbody>
</table>


### Consumes

-   `application/json;charset=UTF-8`

결제 취소 API
-------------

    PUT /api/v1/payments/cancel

### Parameters

<table>
<colgroup>
<col style="width: 11%" />
<col style="width: 16%" />
<col style="width: 50%" />
<col style="width: 22%" />
</colgroup>
<thead>
<tr class="header">
<th>Type</th>
<th>Name</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>Body</strong></p></td>
<td><p><strong>cancelReq</strong><br />
<em>required</em></p></td>
<td><p>cancelReq</p></td>
<td><p><a href="#_cancelreq">CancelReq</a></p></td>
</tr>
</tbody>
</table>

### Responses

<table>
<colgroup>
<col style="width: 10%" />
<col style="width: 70%" />
<col style="width: 20%" />
</colgroup>
<thead>
<tr class="header">
<th>HTTP Code</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>200</strong></p></td>
<td><p>OK</p></td>
<td><p><a href="#_transactionres">TransactionRes</a></p></td>
</tr>
</tbody>
</table>


### Consumes

-   `application/json;charset=UTF-8`

결제 조회 API
-------------

    GET /api/v1/payments/{managementNumber}

### Parameters

<table>
<colgroup>
<col style="width: 11%" />
<col style="width: 16%" />
<col style="width: 50%" />
<col style="width: 22%" />
</colgroup>
<thead>
<tr class="header">
<th>Type</th>
<th>Name</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>Path</strong></p></td>
<td><p><strong>managementNumber</strong><br />
<em>required</em></p></td>
<td><p>결제 관리 번호</p></td>
<td><p>string</p></td>
</tr>
</tbody>
</table>

### Responses

<table>
<colgroup>
<col style="width: 10%" />
<col style="width: 70%" />
<col style="width: 20%" />
</colgroup>
<thead>
<tr class="header">
<th>HTTP Code</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>200</strong></p></td>
<td><p>OK</p></td>
<td><p><a href="#_paymentres">PaymentRes</a></p></td>
</tr>
</tbody>
</table>


### Produces

-   `application/json;charset=UTF-8`

Definitions
===========

CancelReq
---------

<table>
<colgroup>
<col style="width: 42%" />
<col style="width: 57%" />
</colgroup>
<thead>
<tr class="header">
<th>Name</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>amount</strong><br />
<em>required</em></p></td>
<td><p>integer (int64)</p></td>
</tr>
<tr class="even">
<td><p><strong>managementNumber</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="odd">
<td><p><strong>valueAddedTax</strong><br />
<em>optional</em></p></td>
<td><p>integer (int32)</p></td>
</tr>
</tbody>
</table>


PaymentReq
----------

<table>
<colgroup>
<col style="width: 16%" />
<col style="width: 61%" />
<col style="width: 22%" />
</colgroup>
<thead>
<tr class="header">
<th>Name</th>
<th>Description</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td><p><strong>amount</strong><br />
<em>required</em></p></td>
<td><p>결재금액</p></td>
<td><p>integer (int64)</p></td>
</tr>
<tr class="even">
<td><p><strong>cardNumber</strong><br />
<em>required</em></p></td>
<td><p>카드번호</p></td>
<td><p>string</p></td>
</tr>
<tr class="odd">
<td><p><strong>cvc</strong><br />
<em>required</em></p></td>
<td><p>cvc</p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>installmentMonth</strong><br />
<em>required</em></p></td>
<td><p>할부개월수</p></td>
<td><p>integer (int32)</p></td>
</tr>
<tr class="odd">
<td><p><strong>validity</strong><br />
<em>required</em></p></td>
<td><p>유효기간</p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>valueAddedTax</strong><br />
<em>optional</em></p></td>
<td><p>부가가치세</p></td>
<td><p>integer (int32)</p></td>
</tr>
</tbody>
</table>

PaymentRes
----------

<table>
<colgroup>
<col style="width: 42%" />
<col style="width: 57%" />
</colgroup>
<thead>
<tr class="header">
<th>Name</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="even">
<td><p><strong>amount</strong><br />
<em>required</em></p></td>
<td><p>integer (int64)</p></td>
</tr>
<tr class="odd">
<td><p><strong>cardNumber</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>cvc</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="odd">
<td><p><strong>paymentId</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>paymentType</strong><br />
<em>required</em></p></td>
<td><p>enum (PAYMENT, CANCEL)</p></td>
</tr>
<tr class="odd">
<td><p><strong>validity</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
 <tr class="odd">
<td><p><strong>additionalInfo</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
</tbody>
</table>



TransactionRes
--------------

<table>
<colgroup>
<col style="width: 42%" />
<col style="width: 57%" />
</colgroup>
<thead>
<tr class="header">
<th>Name</th>
<th>Schema</th>
</tr>
</thead>
<tbody>
<tr class="even">
<td><p><strong>managementNumber</strong><br />
<em>required</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="odd">
<td><p><strong>additionalInfo</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
</tbody>
</table>



---

## 테이블 설계

#### payment : 결제 정보 저장 테이블 (payment_card_info table과 many to one 관계 )

| Column                   | Type         | Description                |
| ------------------------ | ------------ | -------------------------- |
| id                       | bigint(20)   | auto_increment             |
| amount                   | bigint(20)   | 결제 금액                  |
| Installment_month        | int(11)      | 할부 정보                  |
| Management_number        | varchar(20)  | 관리 번호                  |
| Origin_management_number | varchar(20)  | 원거래 관리 번호           |
| payment_type             | varchar(255) | 결제 구분을 위한 enum 값   |
| transfer_success         | tinyint(1)   | 카드사 전문 송신 성공 여부 |
| value_added_tax          | int(11)      | 부가세 정보                |
| payment_card_info        | varchar(20)  | payment_card_info table FK |
| create_time_at           | datetime     | 생성일시                   |

#### payment_card_info : 카드 정보 저장 테이블 

| Column            | Type         | Description               |
| ----------------- | ------------ | ------------------------- |
| id                | bigint(20)   | auto_increment            |
| encrypt_card_info | varchar(255) | 암호화되어 있는 카드 정보 |
| payment_id        | varchar(20)  | 관리번호와 맵핑           |
| create_time_at    | datetime     | 생성일시                  |

#### card_company : 카드사 전송 전문 저장 테이블 

| Column            | Type        | Description        |
| ----------------- | ----------- | ------------------ |
| management_number | varchar(20) | 관리번호           |
| telegram          | Text        | 규약에 맞춰진 전문 |
| create_time_at    | datetime    | 생성일시           |

---

## 문제 해결

1. API 구현

   * 명세에 정의 되어 있는 필수/옵션 필드가 정의 된 `Dto` 생성 후 내부 `Entity`로 변환 하기 위해 `ModelMapper`를 사용함
     * 결제, 결제취소, 조회 `requestDto`에 `mapping` 되는 `PropertyMap`적용

   

2. 관리 번호 생성 (unique id, 20자리) 

   * `org.apache.commons.lang3.RandomStringUtils`  을 통해 숫자/문자 생성
     * 사용된 알고리즘 검증 진행 하지 못 함.

3. 암/복호화 

   * 대칭형 암호 알고리즘 중 블록암호 알고리즘 방식인 AES128 암호화 적용

4. 카드사 전송 전문 셍성 ( string 데이터 )

   * 전문에 사용하는 데이터 타입 `enum` 정의
   * 전문 규약에 맞는 필드 `name, length, data type` 정의
   * 정의된 전문 규약을 통해 정해진 `String 전문 생성` 

5. (선택) 부분 취소 API Test case **통과**

   * TODO

6. (선택) Multi Thread 환경에 대비

   * Todo: 