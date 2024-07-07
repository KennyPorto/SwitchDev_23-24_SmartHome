scp -i ~/.ssh/id_rsa_dei -r src/main/resources/static/* root@10.9.24.154:/var/www/dist/

ssh -i ~/.ssh/id_rsa_dei root@10.9.24.154 'sudo service nginx restart'