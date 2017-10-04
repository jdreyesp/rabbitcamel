# RabbitMQ Apache Camel examples

Here you'll find some examples with RabbitMQ - Apache Camel integration

## Prerequisites

You need to have RabbitMQ up and running on your machine. To reach that:

1. Download RabbitMQ server:
  - Visit https://www.rabbitmq.com/install-standalone-mac.html to download RabbitMQ standalone for MAC
  - or. Visit https://www.rabbitmq.com/install-windows.html to download RabbitMQ standalone for Windows

(For MAC users)
2. Extract file to a directory

3. Start the server in the /sbin folder in your extracted directory : 

```bash 
./rabbitmq-server
```

4. Enable rabbitmqadmin plugin so that you can check your exchanges / queues on the administration console. 
  - in your /sbin folder: 
  ```bash 
  ./rabbitmq-plugins enable rabbitmq_management 
  ```
  
  - Visit https://www.rabbitmq.com/management.html for more info

5. Visit localhost:15672

## Run the project

- For each example you'll find a consumer and sometimes a producer. Just run the main classes attached to them.

