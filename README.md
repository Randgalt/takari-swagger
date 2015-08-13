# takari-swagger

Utilities for working with Swagger

## Background

[Swagger](http://swagger.io/) is a system for documenting RESTful APIs. It consists of libraries and a [web-based console](http://swagger.io/swagger-ui/). The libraries can be cumbersome to use and bring in many unwanted dependencies. Takari-Swagger is a simpler library that allows publishing the data needed by the Swagger console without most of the overhead.

## Modules

* *takari-swagger-core* - library for generating Swagger JSON via reflection. 
* *takari-swagger-bundle* - JAX-RS resource for publishing Swagger JSON plus a bundled version of the Swagger console

## How To Use

1. Allocate a `SwaggerBuilder` via `Swagger.builder()`
2. Pass in the list of Resource classes to analyze via `jaxRsClasses()`
3. Set the base path for the APIs via `basePath()`
4. Finally, call `build()`
 
The returned `Swagger` instance can be used to return the Swagger JSON. Either use takari-swagger-bundle for this or provide your own (see ApiDocsResource.java for an example)
