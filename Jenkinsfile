pipeline {
	agent any
	environment {
		JAR_NAME = "SpringRecipeAIProject-0.0.1-SNAPSHOT.jar"
		DOCKER_IMAGE = "yuhobin/ai-app:latest"
		SERVER_USER = "ubuntu"
		SERVER_IP = "43.203.176.193"
		APP_DIR = "/home/ubuntu/app"
	}

	stages {
		stage("Repository Checkout"){
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		
		stage("JDK & Gradle Check") {
			steps {
				sh '''
				    java -version
				    # gradlew 실행 권한을 먼저 부여한 후 버전을 확인합니다.
				    chmod +x gradlew
				    ./gradlew --version
				   '''
			}
		}
		
		stage("Gradlew Build") {
			steps {
				sh '''
				     ./gradlew clean build -x test
				   '''
			}
		}
		
		stage("Docker Build") {
			steps {
				sh '''
				    docker build -t ${DOCKER_IMAGE} .
				   '''
			}
		}
		
		stage("DockerHub Login") {
			steps {
				withCredentials([
					usernamePassword(
						credentialsId: 'dockerhub_info',
						usernameVariable: 'DH_USER',
						passwordVariable: 'DH_PASS'
					)
				]){
					sh '''
					    echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
					   '''
				}
			}
		}
		
		stage("DockerHub Push") {
			steps {
				sh '''
				    docker push ${DOCKER_IMAGE}
				   '''
			}
		}
		
        stage("SSH Key Setting"){
			steps {
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
					    mkdir -p ~/.ssh
					    cp "$SSH_KEY" ~/.ssh/id_ed25519
					    chmod 600 ~/.ssh/id_ed25519
					   '''
				}
			}
		}
		
		stage("Known Hosts"){
			steps {
				sh '''
				    mkdir -p ~/.ssh
				    ssh-keyscan -H ${SERVER_IP} >> ~/.ssh/known_hosts
				    chmod 644 ~/.ssh/known_hosts
				   '''
			}
		}
		
		stage("Create .env"){
			steps {
				withCredentials([
					string(credentialsId: 'post-url', variable: 'POST_URL'),
					string(credentialsId: 'gen-key', variable: 'GEN_KEY'),
					sshUserPrivateKey(credentialsId: 'SERVER_SSH_KEY', keyFileVariable: 'SSH_KEY', usernameVariable: 'SSH_USER')
				]){
					sh '''
					   ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} <<EOF 
					   mkdir -p ${APP_DIR}
					   cd ${APP_DIR}
					   
					   rm -f .env
					   echo "SPRING_PROFILES_ACTIVE=prod" > .env
					   echo "POST_URL=${POST_URL}" >> .env
					   echo "GEN_KEY=${GEN_KEY}" >> .env
					   
					   chmod 600 .env
						EOF
					   '''
				}
			}
		}

        stage("Copy Docker-Compose"){
			steps {
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
					    ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} "mkdir -p ${APP_DIR}"
					    scp -i "$SSH_KEY" -o StrictHostKeyChecking=no docker-compose.yml ${SERVER_USER}@${SERVER_IP}:${APP_DIR}/docker-compose.yml
					   '''
				}
			}
		}
		
		
		stage("Deploy"){
			steps {
				withCredentials([
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
						   ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} "
						       cd ${APP_DIR}
						       sudo docker compose down || true
						       sudo docker compose pull
						       sudo docker compose up -d
						   "
						'''
				}
			}
		}
		
	}
	
	post {
		success {
			echo '======================='
			echo 'Docker Compose 배포 성공'
			echo '======================='
		}
		failure {
			echo '======================='
			echo 'Docker Compose 배포 실패'
			echo '======================='
			withCredentials([
				sshUserPrivateKey(
					credentialsId: 'SERVER_SSH_KEY',
					keyFileVariable: 'SSH_KEY',
					usernameVariable: 'SSH_USER'
				)
			]){
				sh '''
				   ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} "cd ${APP_DIR} && sudo docker compose ps" || true
				   '''
			}
		}
	}
}