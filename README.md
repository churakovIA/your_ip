# Web-Сервис для опредления вашего IP

Приложение доступно по адресу: https://your-ip-2.herokuapp.com

## API

### Ответ текстовом формате
GET https://your-ip-2.herokuapp.com
```
127.0.0.1
```

### Ответ формате xml
GET https://your-ip-2.herokuapp.com/?format=xml
```xml
  <RequestInfo>
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

### Ответ формате json
GET https://your-ip-2.herokuapp.com/?format=json
```json
{"ip": "127.0.0.1"}
```

### POST запрос. Ответ формате xml с body
POST https://your-ip-2.herokuapp.com

My super body

```xml
  <RequestInfo>
    <IP>127.0.0.1</IP>
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
    <Body><![CDATA[My super body]]></Body>
  </RequestInfo>
```
