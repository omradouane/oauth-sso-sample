#oauth-sso-server
The oauth server with SSO

* a) Browse with your preferred navigator the following URL :
```
http://localhost:60947/auth/oauth/authorize?response_type=code&client_id=SampleAppOne&redirect_uri=http://localhost:60947/auth/sso/me
```

* b) Exchange the Authorization Code for an Access Token
```
curl -u SampleAppOne:secret http://localhost:60947/auth/oauth/token -d 'grant_type=authorization_code&redirect_uri=http://localhost:60947/auth/sso/me&code=AUTHORIZATION_CODE'
```

The result should be like : 
```
{
  "access_token": "a1991b7c-abeb-4fa7-a000-32ea9f078f7f",
  "token_type": "bearer",
  "expires_in": 41559,
  "scope": "read write"
}
```

## password grant_type
```
curl -H "Authorization: Bearer $(curl SampleAppOne:secret@localhost:60947/uaa/oauth/token -d "grant_type=password&username=user&password=pass" | jq --raw-output .'access_token')" localhost:60947/uaa/sso/hellouser
```

## autorization_code grant_type
Browse with user credentials :
http://localhost:60947/uaa/oauth/authorize?response_type=code&client_id=SampleAppOne&redirect_uri=http://localhost:60947/uaa/sso/me
Get token using Client credential 
curl -u SampleAppOne:secret http://localhost:60947/uaa/oauth/token -d 'grant_type=authorization_code&redirect_uri=http://localhost:60947/uaa/sso/me&code=AUTHORIZATION_CODE' | jq .
Then get the resource using TOKEN
curl -H "Authorization: Bearer TOKEN" localhost:60947/uaa/sso/me
