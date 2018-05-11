#oauth-sso-server
The oauth server with SSO
## autorization_code grant_type

* a) Browse with your preferred navigator the following URL :
```
http://localhost:60947/uaa/oauth/authorize?response_type=code&client_id=SampleAppOne&redirect_uri=http://localhost:60947/uaa/sso/me
```

* b) Exchange the Authorization Code for an Access Token
```
curl -u SampleAppOne:secret http://localhost:60947/uaa/oauth/token -d 'grant_type=authorization_code&redirect_uri=http://localhost:60947/uaa/sso/me&code=AUTHORIZATION_CODE' | jq .
```

The result should be like (Using JWT): 
```
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX3NzbyJdLCJ1c2VyX25hbWUiOiJyb290Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTUyNjA3ODIxNiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIzYjQwZWJkNi0yYWQ5LTQ1ZjUtYjJkYy02Y2MwNTY2YzBhNjIiLCJjbGllbnRfaWQiOiJTYW1wbGVBcHBPbmUifQ.gKNUvQG3RfO6V4_sbdY8aVTbUhSZkN_juxRmouYJW5I",
  "token_type": "bearer",
  "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsib2F1dGgyX3NzbyJdLCJ1c2VyX25hbWUiOiJyb290Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6IjNiNDBlYmQ2LTJhZDktNDVmNS1iMmRjLTZjYzA1NjZjMGE2MiIsImV4cCI6MTUyODYyNzAxNiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI2ZWM3ZGFhNS05OWQyLTQ0M2MtYmU5MC03MWRmYTI1OWZhYjUiLCJjbGllbnRfaWQiOiJTYW1wbGVBcHBPbmUifQ.RDpZb_JqfylumbCxrpjmr2SJJ7puBgTpoTIoAbaMFho",
  "expires_in": 43199,
  "scope": "read write",
  "jti": "3b40ebd6-2ad9-45f5-b2dc-6cc0566c0a62"
}
```

* c) Then get the resource using the Access Token
```
curl -H "Authorization: Bearer TOKEN" localhost:60947/uaa/sso/me
```

## password grant_type
```
curl -H "Authorization: Bearer $(curl SampleAppOne:secret@localhost:60947/uaa/oauth/token -d "grant_type=password&username=user&password=pass" | jq --raw-output .'access_token')" localhost:60947/uaa/sso/hellouser
```

#sample-app-one
Open your preferred browser and navigate to the appOne sample endpoint
```
http://localhost:60948/appone/sample
```
You should be redirected to Authorization server
```
http://localhost:60947/uaa/login
```
Login using ```root/root``` or ```user/pass``` credentials

Once authentication is success you will be redirected to ```http://localhost:60948/appone/sample``` endpoint and get a result similar to the following :
```
Hi root from app ONE
```



#sample-app-two
Open an other tab of your browser and navigate to the appTwo sample endpoint
```
http://localhost:60949/apptwo/sample2
```

You will get the following result without being authenticated.

```
Hi root from app TWO
```