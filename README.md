#! old-smoke

## Introduction

old-smoke is a REST HTTP Service that can generate random address data for a testing framework.
The address does not have to be logical/shipping valid.
It is supposed to be in a human-readable country mailing format.
For example "532 Union St., Apt 72, San Diego, NY, 789302" is acceptable even though it would not pass USPS validation.

The service is limited to the following countries:  US, Canada, Mexico, and the Netherlands.

The service is implemented in Spring Boot.  When the user makes a GET request to "/randomizer/address", the service returns a JSON object containing the following fields:

| ----------- | ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| field       | Description                                                                                                                  | Implementation                              |
| ----------- | ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| house       | House or street number.                                                                                                      | a random 5 digit number                     |
| street      | Street name (in practice may also contain street number).                                                                    | Falton Drive NE, MISIÓN DE SAN LORENZO, ... |
| postalCode  | An alphanumeric string included in a postal address to facilitate mail sorting (a.k.a. post code, postcode, or ZIP code).    | a random 9 digit number                     | 
| City        | The name of the primary locality of the place.                                                                               | Cloverdale Villahermosa, ...                |
| county      | A division of a state; typically a secondary-level administrative division of a country or equivalent.                       | Edmonton, Centro                            |
| state       | optional A division of a country; typically a first-level administrative division of a country and/or a geographical region. | Alberta, Tabasco, ...                       |
| stateCode   | optional A code/abbreviation for the state division of a country.                                                            | AB, TAB, ...                                |
| country     | optional The localised country name.                                                                                         | Canada, Mexico                              |
| countryCode | (ISO 3166-1 alpha-3 code) A three-letter country code.                                                                       | CAN, MEX                                    |
| ----------- | ---------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------- |

The house and postalCode fields are random 5 and 9 digit numbers.
The other fields are randomly chosen from 4 example values.
For example, the countryCode will be randomly chosen from "CAN", "MEX", "NLD", and "USA".

The states are 4 states from each of Canada, Mexico, the Netherlands, and the United States of America; but no effort has been made to link them.
So some of the random addresses will be shipping invalid (e.g., a Mexican state Tabasco combined with Canada).
Indeed none of the fields are consistent (e.g., MEX country code with Canada country).
This may or may not be a feature depending on the needs of the testing framework.

## Usage

You will need maven to run this program.
maven should download all other dependencies.

Clone this project to your computer and change directories to the project directory.

To build, test, and run execute `mvn clean checkstyle:checkstyle test verify org.pitest:pitest-maven:mutationCoverage org.springframework.boot:spring-boot-maven-plugin:run`.
This will
1. download dependencies
2. check the style of the project - to verify that there is no ugly looking code
3. run the unit tests
4. run the integration tests
5. run the mutation tests
6. run the application (it will respond to http://127.0.0.1:8080/randomizer/address)

## Program

### Implementation

#### Address

I created an Address Bean class that represents the various fields of an Address in one JAVA class.
I used an interface to define the Bean.
I implemented the Bean with an anonymous class.
I think this is a good way to implement immutable Beans.

I used a special annotation to indicate that the City field should be serialized to JSON differently.

#### AddressService

I created an AddressService to create Random beans.
This service is in fact deterministic and the supposed randomness comes from and injected Random.
The service uses the Random to generate random address fields and return an Address bean with those fields.

#### AddressController

I created an AddressController to respond to requests for service.
When the user makes a get request to "/randomizer/address" this controller will use the AddressService to create a random address and then return it to the user with an already expired cache.
We expire the cache because we should not be caching random data - it is always changing.

#### ErrorController

I created an ErrorController to respond to error events.
It does not much more than redirect the user to "/randomizer/address".
Perhaps I should make it log the error, but I did not.

### Testing

I utilized three types of testing:  unit tests, integration tests, and manual tests.
I consider mutation testing to be a separate category from testing (but the terminology is confusing).

#### Unit Tests

My goal is to test a unit of code in isolation (assuming that everything else was implemented correctly).
My unit tests are all the files ending in "Test.java".
They are in the src/test directory.

I use mockito to mock out the other components.

#### Integration Tests

My goal is to test the project as a whole.
I use RESTAssured.
It starts the program on a random port.
Then it hits "/randomizer/address" with 5 GET requests.

Since the program is in fact deterministic (the random SEED is hard coded) these GET requests should always return the 5 same "random" addresses.
As a developer, the real testing is done by looking at the specs.
Informally, the addresses look random enough to me.
If I saw for example that it always returned the same city then I would investigate.

From a black box perspective, it really is a probabilistic test.
Given that I took the first 5 addresses, there is no guarantee that the program would start returning the same address with the 6th one onwards.
But I think it unlikely.
If I wanted to be more sure, I would verify the "random lookingness" of more addresses.

Another restriction on this project that is hard to verify is that the addresses are limited to the four countries.
None of the tested data violates that restriction, but from a black box perspective it is not hard to imagine that there could be a 5th country.
Again I could probabilistically verify this by increasing the number of verifications but I could never get to 100% certainty.
From a white box perspective, I am pretty confident that the program honors the 4 country restriction.

#### Manual Testing

I use my browser to visit "http://127.0.0.1:8080" (note I purposefully hit the wrong address).
I see an error page.
It has a link.
I click the link.

It takes me to "http://127.0.0.1:8080/randomizer/address".
I see an address in JSON format.
It looks good to me.
I hit reload a bunch of times.
I keep seeing addresses that look good to me.

### Quality

The term "mutation testing" suggests that it is another kind of testing like unit tests, integration tests, or manual testing;
but I consider it to be an entirely different category of thing.
I will call this category "quality."

The need for quality is because of the old philosophical problem "Quis custodiet ipsos custodes?" (Who will guard the guards?)
I wrote a program that I believe is high quality.
To verify the program, I wrote a series of tests.
However, the same person who wrote the program wrote the tests.
If I can make a programming mistake, I can make a testing mistake (tests are after all specialized programs).

Since the unit and integration test specs are computer programs, logically we should test them as well.
However, if I wrote specs for testing unit tests then logically I would need to test those specs and the cycle would repeat infinitely.

Clearly that is not sustainable.
Fortunately mutation testing offers a better way.
The mutation testing framework PITest creates a series of "mutants".
Each mutant is a modified version of the original program.
It takes my source code and "mutates" it.

Each mutant is almost exactly identical to the original code except for exactly one mutation.
For example, it might mutate the code "for(int i=0; i<100; i++)" into "for(int i=0; i<=100; i++)".

Then it runs the mutant against the suite of automated tests.
If my test suite is comprehensive (and the original code is correct) then the mutant code has an off by one error.
The test suite detects the off by one error and "kills the mutant."
If all the mutants are "killed" then mutation testing is "passed".

Alternatively, my test suite is not comprehensive (but my original code was correct).
The test suite does not detect the off by one error and the mutant survives.
If a single mutant survives then mutation testing is "failed".

I do not seriously consider the case of my original code being incorrect because if the test suite is comprehensive it will detect the error.
PITest runs the test suite against the original code before even starting the mutation process.
This is why I consider mutation testing to be in a different category from unit or integration testing.
Mutation testing is not testing my implementation code, but instead it is testing my automated testing.

## Discussion

### openaddress.io
When I started this project, I did some research.
There is an existing address service:  openaddress.io.
openaddress.io does not exactly meet the need.
It is a large database of real addresses.

However, it could be leveraged.
The address service could randomly choose from real addresses from openaddress.io.
This would give us "shippable" addresses that are internally consistent.

However, I found that even though openaddress.io addresses seem to meet the requirements (if we map region to county and district to state); the vast majority of addresses have large numbers of empty fields.
I interpreted the requirement as being that most fields were not optional.

### PITest

I am real excited about the concept of mutation testing but I have several concerns about PITest

#### Separation of Unit and Integration Tests

I would like for the mutation analsis to be done twice.
The first time it should run the mutants against the unit tests.
The second time it should run the mutants against the integration tests.
I believe it ran the mutation analysis once against the combined suite of unit and integration tests.
I think this should be achievable with configuration.

One problem that might occur is that I implemented a deprecated method to make the program compile.
This is easily testable with unit tests but is impossible to test with integration tests.
(It is also of no importance - so the fact that I did not test it is not concerning.)
But I would not want to fail mutation testing because of it.

#### Thoroughness

I was expecting more thoroughness from the mutation testing.
A failing mutation test report should motivate me to
1. delete unused code
2. write more tests

What drew me to mutation testing was the realization that "off by one errors" are very easy to make and the computer was very good at checking for them.
Something I noticed in the requirements was that the "City" field was capitalized in a non-standard way.
My integration test does verify the non standard "City" capitalization but the mutation testing did not enforce or suggest that.

Mutation testing could work by creating a mutant with the @JSONProperty annotation removed from the "getCity" method.
The mutant would return a JSON object with the more standard "city" field.
My integration test would look for "City" and not find it.
The integration test would fail.
The mutant would be killed.
This would give me more confidence in my automated tests.