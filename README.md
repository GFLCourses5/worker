### Project type: website testing service

### Application architecture:

![img.png](img.png)

### Contract:

-       Case: add new scenarios
-       Endpoint: "/api/v1/scenarios"
-       Description: "HTTP POST %SERVER_URL%:%SERVER_PORT%/api/v1/scenarios"
-       Request body:

```json 
{
    "scenarios": [
        {
            "name": "Wikipedia Search",
            "site": "https://www.wikipedia.org",
            "steps": [
                {
                    "action": "clickCss",
                    "value": "#js-link-box-en > strong"
                },
                {
                    "action": "sleep",
                    "value": "4"
                },
                {
                    "action": "clickXpath",
                    "value": "//div[2]/span/a"
                },
                {
                    "action": "sleep",
                    "value": "4"
                }
            ]
         }
    ],
    "userId": 1,
    "proxyRequired": true
}
```

-       Case: get results of scenarios:
-       Endpoint: "/api/v1/scenarios/user/{userId}"
-       Description: "HTTP GET %SERVER_URL%:%SERVER_PORT%/api/v1/scenarios/user/{userId}"
-       Response body: 

```json
[
  {
    "id": "0",
    "name": "Wikipedia Search",
    "site": "https://www.wikipedia.org",
    "stepsResults": [
      {
        "action": "clickCss",
        "value": "#js-link-box-en > strong",
        "isPassed": true
      },
      {
        "action": "sleep",
        "value": "4",
        "isPassed": true
      },
      {
        "action": "clickXpath",
        "value": "//div[2]/span/a",
        "isPassed": false
      },
      {
        "action": "sleep",
        "value": "4",
        "isPassed": true
      }
    ],
    "executedAt": "2007-12-03T10:15:30+01:00"
  },
  {
    "id": "0",
    "name": "Wikipedia Search",
    "site": "https://www.wikipedia.org",
    "stepsResults": [
      {
        "action": "clickCss",
        "value": "#js-link-box-en > strong",
        "isPassed": true
      },
      {
        "action": "sleep",
        "value": "4",
        "isPassed": true
      },
      {
        "action": "clickXpath",
        "value": "//div[2]/span/a",
        "isPassed": false
      },
      {
        "action": "sleep",
        "value": "4",
        "isPassed": true
      }
    ],
    "executedAt": "2007-12-03T10:15:30+01:00"
  }
]
```

-       Case: get result of scenario:
-       Endpoint: "/api/v1/scenarios/{id}"
-       Description: "HTTP GET %SERVER_URL%:%SERVER_PORT%/api/v1/scenarios/{id}"
-       Response body: 

```json
{
  "id": "0",
  "name": "Wikipedia Search",
  "site": "https://www.wikipedia.org",
  "stepsResults": [
    {
      "action": "clickCss",
      "value": "#js-link-box-en > strong",
      "isPassed": true
    },
    {
      "action": "sleep",
      "value": "4",
      "isPassed": true
    },
    {
      "action": "clickXpath",
      "value": "//div[2]/span/a",
      "isPassed": false
    },
    {
      "action": "sleep",
      "value": "4",
      "isPassed": true
    }
  ],
  "executedAt": "2007-12-03T10:15:30+01:00"
}
```

-       Case: delete result of scenario:
-       Endpoint: "/api/v1/scenarios/{resultId}"
-       Description: "HTTP DELETE %SERVER_URL%:%SERVER_PORT%/api/v1/scenarios/{resultId}"