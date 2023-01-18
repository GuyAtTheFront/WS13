
# Workshop 14 Solution

## Changes from Workshop 13:

## `ContactRedis.java` file

* This class replaces the functionality provided by `Contacts.java`

* All read / write is now handled by redis. 


## `RedisConfig.java` file

* This is jedis-redis configuration

* Most code here are boilerplate, can be reused with minimal changes. What those changes are depends on how you need jedis-redis to be configured

* The important bit here is how (keys: values) of value / hash objects are serialized 

* This solution uses <String, String> serialization to showcase how default redis can tackle this problem. FYI, teacher's solution uses <String, Object>.


## `MainController.java` file

* This file is where the controllers lives

* All method calls to `Contacts` in WS 13 have been replaced with a suitable method in `ContactRedis.Java`

* There are comments to show those replacement. Old code has been commented out. 


## `Ws14Application.java` file

* We no longer create a file directory to write files to, now that we have redis. Those codes have been commented out. 

* Consequently, there is no need for this app to accept args to function. Args validations have also been commented out.  

* Args are still manually set and passed to Spring. I was lazy. The passed args are not used.


## Final Words

The focus of Workshop 14 is to integrate Redis into our app, and compare its functionalities with using traditional file IO. It is helpful to compare `ContactsRedis.java` with `Contacts.java` to understand the strengths of using Redis (or a database). If you still struggle with Spring MVC, workshops 12 & 13 would be more friendly for you. 

Try to capture the "flow" (config --> repo --> controller). Take note of all the `@Autowired`, and how methods in `ContactsRedis` are eventually used to fulfill the requirements.

\- Guy at the front
