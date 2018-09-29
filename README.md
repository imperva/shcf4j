# shcf4j
SHCF4J is a façade wrapper meant to enforce separation-of-concerns between the application and the HTTP Client used under-the-hoods. The concrete HTTP Client SDK must be injected into the SHCF4J, than, by using SHCF4J developers are forced to use its API which hides the implementation used internally and thus enable developers changing it when needed.
