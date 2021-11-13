docker run --rm --name local-sqs -p 8010:4576 -e SERVICES=sqs -e START_WEB=0 -d localstack/localstack:0.11.1;
QUEUE_URL=`aws sqs create-queue --queue-name=matrix-multiplication-request-queue --profile localstack --endpoint-url=http://localhost:8010`
