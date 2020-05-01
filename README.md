# Web-Сервис для анализа входящего http-запроса

Сервис представляет собой набор методов для получения сведений о входящем запросе - 
ip, headers, body и др. \
Ответ можно получить в различных форматах: _text, xml, json_. \
Может быть использован с целью отладки вашего сервиса. \
Приложение доступно по адресу: https://your-ip-2.herokuapp.com

## API
### API reference contents
- GET /
- GET /?format=xml
- GET /?format=json
- GET[POST] /json
- GET[POST] /xml
- GET[POST] /rq
- GET[POST] /rq/json
- GET[POST] /rq/xml
- GET[POST] /wait/{milliseconds}

### Example Request
#### Ответ текстовом формате
GET https://your-ip-2.herokuapp.com
```
127.0.0.1
GitHub: https://github.com/churakovIA/your_ip
```

#### Получить ip в формате json
GET|POST https://your-ip-2.herokuapp.com/json
```json
{
  "ip": "127.0.0.1"
}
```

#### Получить ip в формате xml
GET|POST https://your-ip-2.herokuapp.com/xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ip>127.0.0.1</ip>
```

#### Получить инфо о get-запросе
GET https://your-ip-2.herokuapp.com/rq
```
Protocol=HTTP/1.1
Method=POST
FullURL=http://your-ip-2.herokuapp.com/rq
Locale=ru-RU
IP=127.0.0.1

content-length=179
host=http://your-ip-2.herokuapp.com
content-type=*/*; charset=UTF-8
connection=Keep-Alive
accept-encoding=gzip,deflate
user-agent=Apache-HttpClient/4.5.9 (Java/11.0.4)
```

#### Получить инфо о post-запросе
POST https://your-ip-2.herokuapp.com/rq

\<req>123\</req>
```
Protocol=HTTP/1.1
Method=POST
FullURL=http://your-ip-2.herokuapp.com/rq
Locale=ru-RU
IP=127.0.0.1

content-length=179
host=http://your-ip-2.herokuapp.com
content-type=*/*; charset=UTF-8
connection=Keep-Alive
accept-encoding=gzip,deflate
user-agent=Apache-HttpClient/4.5.9 (Java/11.0.4)

<req>123</req>
```

#### Получить инфо о get-запросе в формате json
GET https://your-ip-2.herokuapp.com/rq/json
```json
{
  "protocol": "HTTP/1.1",
  "method": "GET",
  "fullURL": "http://your-ip-2.herokuapp.com/rq/json",
  "locale": "ru-RU",
  "ip": "127.0.0.1",
  "headers": {
    "host": "your-ip-2.herokuapp.com",
    "connection": "Keep-Alive",
    "accept-encoding": "gzip,deflate",
    "user-agent": "Apache-HttpClient/4.5.9 (Java/11.0.4)"
  }
}
```

#### Получить инфо о post-запросе в формате json
POST https://your-ip-2.herokuapp.com/rq/json

\<req>123\</req>
```json
{
  "protocol": "HTTP/1.1",
  "method": "POST",
  "fullURL": "http://your-ip-2.herokuapp.com/rq/json",
  "locale": "ru-RU",
  "ip": "127.0.0.1",
  "headers": {
    "content-length": "14",
    "host": "your-ip-2.herokuapp.com",
    "content-type": "*/*; charset=UTF-8",
    "connection": "Keep-Alive",
    "accept-encoding": "gzip,deflate",
    "user-agent": "Apache-HttpClient/4.5.9 (Java/11.0.4)"
  },
  "body": "<req>123</req>"
}
```

#### Получить инфо о get-запросе в формате xml
GET https://your-ip-2.herokuapp.com/rq/xml
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<RequestInfo>
  <Protocol>HTTP/1.1</Protocol>
  <Method>GET</Method>
  <FullURL>http://your-ip-2.herokuapp.com/rq/xml</FullURL>
  <Locale>ru-RU</Locale>
  <IP>127.0.0.1</IP>
  <Headers>
    <Header name="host">your-ip-2.herokuapp.com</Header>
    <Header name="connection">Keep-Alive</Header>
    <Header name="accept-encoding">gzip,deflate</Header>
    <Header name="user-agent">Apache-HttpClient/4.5.9 (Java/11.0.4)</Header>
  </Headers>
</RequestInfo>
```

#### Получить инфо о post-запросе в формате xml
POST https://your-ip-2.herokuapp.com/rq/xml

\<req>123\</req>
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<RequestInfo>
  <Protocol>HTTP/1.1</Protocol>
  <Method>POST</Method>
  <FullURL>http://your-ip-2.herokuapp.com/rq/xml</FullURL>
  <Locale>ru-RU</Locale>
  <IP>127.0.0.1</IP>
  <Headers>
    <Header name="content-length">14</Header>
    <Header name="host">your-ip-2.herokuapp.com</Header>
    <Header name="content-type">*/*; charset=UTF-8</Header>
    <Header name="connection">Keep-Alive</Header>
    <Header name="accept-encoding">gzip,deflate</Header>
    <Header name="user-agent">Apache-HttpClient/4.5.9 (Java/11.0.4)</Header>
  </Headers>
  <Body><![CDATA[<req>123</req>]]></Body>
</RequestInfo>
```

#### Ответ формате xml
GET https://your-ip-2.herokuapp.com/?format=xml
```xml
  <RequestInfo>
    <Protocol>HTTP/1.1</Protocol>
    <Method>GET</Method>
    <FullURL>https://your-ip-2.herokuapp.com/?format=xml</FullURL>
    <Locale>ru-RU</Locale>
    <IP>93.100.53.35</IP>
    <Headers>
      <sec-fetch-mode>navigate</sec-fetch-mode>
      <x-request-id>ec51f718-7ec0-4384-9ce4-07462a13ae9c</x-request-id>
      ...
      <user-agent>
        Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
        Chrome/80.0.3987.132 Safari/537.36
      </user-agent>
      <sec-fetch-dest>document</sec-fetch-dest>
    </Headers>
  </RequestInfo>
```

#### Ответ формате json
GET https://your-ip-2.herokuapp.com/?format=json
```json
{
  "protocol": "HTTP/1.1",
  "method": "GET",
  "fullURL": "http://localhost:8080/?format=json",
  "locale": "ru-RU",
  "ip": "127.0.0.1",
  "headers": {
    "host": "localhost:8080",
    "connection": "Keep-Alive",
    "accept-encoding": "gzip,deflate",
    "user-agent": "Apache-HttpClient/4.5.9 (Java/11.0.4)"
  }
}
```

#### Вернет ответ с задержкой 600мс
GET https://your-ip-2.herokuapp.com/wait/600
```
wait=600ms
```
