# CalculaOras [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/Gaboso/CalculaOras)

It is a project to generate an absence justification sheet in PDF, and also the project itself already calculates (random) daily hours based on the
informed workload.

Based on an HTML template with _Thymeleaf_ tags, some conversions are performed using _Flying Saucer_, _IText_ and _JTidy_ to obtain a PDF file.

<p align="center" width="100%">
    <img src="img/diagram.png" alt="diagram">
</p>

## Config

In the `resources` directory, there is a `config.properties` file, which contains all the settings used by the application.

| Property                   | Description                                                                                                     | Example Value                    |
|----------------------------|-----------------------------------------------------------------------------------------------------------------|----------------------------------|
| minutes.workedPerDay       | number of minutes worked in a day                                                                               | 480                              |
| worker.name                | worker's name                                                                                                   | John Doe                         |
| worker.socialSecurity.id   | worker's social security number                                                                                 | 60701020304                      |
| employer.name              | employer's name                                                                                                 | Fun Toys Inc.                    |
| employer.socialSecurity.id | employer's social security number                                                                               | 60701020304050                   |
| days                       | array of days to be used by the application, each day must be separated by a comma                              | 01/01/2019,01/02/2019,03/02/2019 |
| days.custom                | if marked as true, it will use the `days` property to generate the days, otherwise it will leave the days blank | true                             |
| days.quantity              | number of days to be generated                                                                                  | 3                                |
| days.enable.justification  | enable absence justification line for all days                                                                  | false                            |
| output.file                | file name to be generated                                                                                       | test.pdf                         |

## How to build

Run the __`build.bat`__ file for _Windows_ or __`build.sh`__ for _Linux_, both are in the root directory of this project

> After execution, the file __`scriptum-x.x.x.jar`__ will be generated in `target` directory

## How to use

After build, run following command to start:

```shell
java -jar scriptum.jar
```

## Output example:

![Image of PDF](img/screenshot.png "PDF Gerado")


