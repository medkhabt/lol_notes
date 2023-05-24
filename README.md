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
