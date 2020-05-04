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
<em>optional</em></p></td>
<td><p>integer (int64)</p></td>
</tr>
<tr class="even">
<td><p><strong>managementNumber</strong><br />
<em>optional</em></p></td>
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
<tr class="odd">
<td><p><strong>additionalInfo</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>amount</strong><br />
<em>optional</em></p></td>
<td><p>integer (int64)</p></td>
</tr>
<tr class="odd">
<td><p><strong>cardNumber</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>cvc</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="odd">
<td><p><strong>paymentId</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>paymentType</strong><br />
<em>optional</em></p></td>
<td><p>enum (PAYMENT, CANCEL)</p></td>
</tr>
<tr class="odd">
<td><p><strong>validity</strong><br />
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
<tr class="odd">
<td><p><strong>additionalInfo</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
<tr class="even">
<td><p><strong>managementNumber</strong><br />
<em>optional</em></p></td>
<td><p>string</p></td>
</tr>
</tbody>
</table>

## Requirement

## 문제 해결
