Solution
===================

This project contain solution to the described [PII Problem](ThePIIProblem.pdf)

### Prerequisites

* JDK 8

### Run unit test
`$ ./gradlew clean test`

### Setup
`$ export PATH=$PATH:.`

### Run program

`$ Solution < Test-inputfile.txt > output.txt`

### Sample output

```
{
  "entries" : [
    {
      "color" : "yellow",
      "firstname" : "Jamie",
      "lastname" : "Stevenson",
      "phonenumber" : "028-164-6574",
      "zipcode" : "84880"
    },
    {
      "color" : "purple",
      "firstname" : "Sam T.",
      "lastname" : "Washington",
      "phonenumber" : "353-791-6380",
      "zipcode" : "85360"
    }
  ],
  "errors" : [
    1,
    3,
    4
  ]
}
```
