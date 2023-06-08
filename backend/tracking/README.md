# lol_notes

If anyone has any technical or feature like suggestion, please feel free to mention it, or also if you think something will lead to problems or it is a smelly code. Your help would be appreciated. 
 
## Complication with the riot client certification
To make sure that the project trust the certification presented by the rclient
that is required when we want to consume from the api `https://localhost:2999/liveclientdata`
I had to make sure to export the certificate from the url to one of the endpoints in the navigator
-  Click on the left side of the url bar of the navigator
-  Click on certification button
-  Detail => export (to .cer)

I kept the certification in the project folder but I am not sure 
if it should be public in the github repo, for now it is not.

Then I had to add the certificate to the keystore created 
for this project available in the `resources/certificats` folder with
the command `keytool -importcert -file path/to/certificat/rclient.cer -keystore path/to/keygencerts.jks -alias riot-client`
and in the configuration file for the RestTemplate, It loads the load the trusted certificates from 
the trust storestore (our keygen) using the configs in the `applications.properties`

## Credentials 
For now the dev-key provided from riot that is used in the calls to riot api
is in the folder `/resources/credentials` that is not present in the repo for
security matter, you should generate your own key and add it to the folder with 
the name `dev_key.txt` or change the path in the `applications.properties`. 

This stays like this until I look for a better way to store sensible data, Or to encrypt them.

## IDE
I am using Intellij IDE, I run through some configuration problems when a intellij solution suggestion
that requires to upgrade java to 14. I downgraded after to solve the problem but it took more time that it
is worth. I downgraded to 8 to be sure. but I was using some methods for `java.util.Optional` and `java.util.Map`
It may seem not persuasive enough to upgrade, but java SE 11 was released in 2018, and it is stable with a Long
time support. 

So I won't spend much time on this issue in the future here is how to configure the version in intellij

https://blog.csdn.net/windnolose/article/details/120842181

On top of that check `pom.xml` change java version as it overrides the intellij module config, 
and check for this plugin's configuration, it should be the same version as your projects version.
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <source>11</source>
        <target>11</target>
    </configuration>
</plugin>
```
*🔄 LastUpdate: Unknown*