# About
TMM LRA demo Flight Express application.
Default TRM LRA coordinator URL is "http://localhost:9000/api/v1/lra-coordinator"
## Quick Start
To install this dependency use:

```
npm install
```
To run the server in development mode:
```
npm run dev
```
To run the server in production mode:
```
npm run prod
```
To run the server with different TRM coordinator assign the URL to ORACLE_TMM_TCS_URL environment variable:
```
export ORACLE_TMM_TCS_URL=<URL>
export TNS_ADMIN=/home/oracle/labs/wallets

npm run <prod|dev>
```
To create a docker image:
```
docker image build -t=<image_name> .
```
To run the docker image:
```
docker container run <image_name>
```
To run the docker image with different TRM coordinator assign the URL to ORACLE_TMM_TCS_URL environment variable:
```
docker container run -e ORACLE_TMM_TCS_URL=<URL> <image_name>
```
To change the Max Booking Count:
```
#set the count value as required

curl --location --request PUT 'http://localhost:8083/flightService/api/maxbookings?count=7'
```