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
<?xml version="1.0" encoding="UTF-8"?>
<ip>127.0.0.1</ip>
```

### Ответ формате json
GET https://your-ip-2.herokuapp.com/?format=json
```json
{"ip": "127.0.0.1"}
```
