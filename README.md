In the JWT auth process, the front end (client) firstly sends some credentials to authenticate itself (username and password in our case, since we're working on a web application).

The server (the Spring app in our case) then checks those credentials, and if they are valid, it generates a JWT and returns it.

After this step client has to provide this token in the request’s Authorization header in the “Bearer TOKEN” form. The back end will check the validity of this token and authorize or reject requests. The token may also store user roles and authorize the requests based on the given authorities

We need authentication to make sure that the user is really who they claim to be. We will be using the classic username/password pair to accomplish this.

Here are the steps to implement authentication:

1. Create our <b>Authentication Filter</b> that extends <b>UsernamePasswordAuthenticationFilter</b>.
2. Create a security configuration class that extends <b>WebSecurityConfigurerAdapter</b> and apply the filter.

We also need an <b>Authorization filter</b>. This filter will check the existence and validity of the access token on the Authorization header. We will specify which endpoints will be subject to this filter in our configuration class.

Now, we need to apply both of these filters through a configuration class. We call it <b>WebSecurityConfig</b> class and annotate it with <b>@EnableWebSecurity</b> as well as extend <b>WebSecurityConfigureAdapter</b> to implement our custom security logic.