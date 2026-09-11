pipeline {
	/*
	    소기업 : Git Action
	    중소기업 : Jenkins 
	    대기업 : 자체 처리 
	      = docker , docker-compose
	    전체 동작 : Jenkins = 관리자 
	    Git Push 
	       |------ workflows(Git)
	       |------ WebHook (트리거)
	    Jenkins 
	       |------ Permission 방지 
	               cnmod +x gradlew : 실행 권한 
	   Gradle Build
	       |------ ./gradlew clean build -x test test제외 jar
	    Docker Build 
	       |------ image만든다 docker build -t image명 
	    Docker Hub Push docker push image명 
	       |------ 서버 종료
	    Docker compose down
	       |
	    Docker compose Pull 
	       |
	    Docker compose up -d  
	       
	*/
	agent any
	// 변수 설정 
	environment {
		JAR_NAME = "SpringRecipeAIProject-0.0.1-SNAPSHOT.jar"
		DOCKER_IMAGE = "yuhobin/ai-app:latest"
		// AWS EC2
		SERVER_USER="ubuntu"
		SERVER_IP="43.203.176.193"
		APP_DIR="/home/ubuntu/app"
	}
	// 우분투 (AWS) 명령어 수행 
	/*
	   scm 
	     = git-url 
	     = Jenkinsfile인식 
	*/
	stages {
		// 1. Git Checkout : Repository확인 
		stage("Repository Checkout"){
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		// 2. Java = JDK확인 
		stage("JDK21 확인"){
			steps {
				sh '''
				    java -version
				    
				   '''
			}
		}
		// yml 인식 => ${POST_URL} , api-key : ${GEN_KEY}
		
		// 3. gradlew 실행 권한 
		stage("Gradle Permission") {
			steps {
			   sh '''
			        chmod +x gradlew
			      '''	
			}
		}
		
		// 4. gradlew build => 배포파일 만들기 (jar)
		stage("Gradlew Build") {
			steps {
				sh '''
				     ./gradlew clean build -x test
				   '''
			}
		}
		
		// 5. Docker Image => 시간 측정 
		stage("Docker Build") {
			steps {
				sh '''
				    docker build -t ${DOCKER_IMAGE} .
				   '''
			}
		}
		
		// 6. Docker Hub Login
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
					    echo "$DH_PASS" docker login -u "$DH_USER" --password-stdin
					   '''
				}
			}
		}
		// 7. Dockerhub  Push
		stage("DockerHub Push") {
			steps {
				sh '''
				    docker push ${DOCKER_IMAGE}
				   '''
			}
		}
		
		// 8. SSH KEY 설정 SERVER_SSH_KEY
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
		// 9. AWS 접근 
		stage("Known Hosts"){
			steps {
				sh '''
				    mkdir -p ~/.ssh
				    ssh-keyscan -H 43.203.176.193 >> ~/.ssh/known_hosts
				    
				    chmod 644 ~/.ssh/known_hosts
				   '''
			}
		}
		// 10. .env생성 
		stage("Create .env"){
			steps {
				withCredentials([
					string(
						credentialsId: 'post-url',
						variable: 'POST_URL'
					),
					string(
						credentialsId: 'gen-key',
						variable: 'GEN_KEY'
					),
					sshUserPrivateKey(
						credentialsId: 'SERVER_SSH_KEY',
						keyFileVariable: 'SSH_KEY',
						usernameVariable: 'SSH_USER'
					)
				]){
					sh '''
					   ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@43.203.176.193 \
					   "mkdir -p /home/ubuntu/app && \
					   cd /home/ubuntu/app && \
					   rm -f .env && \
					   echo "SPRING_PROFILES_ACTIVE=prod" > .env && \
					   echo "POST_URL=${POST_URL}" >> .env && \
					   echo "GEN_KEY=${GEN_KEY}" >> .env && \
					   chmod 600 .env"
					   '''
				}
			}
		}

        // 8. docker-compose.yml 이동 
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
					    ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@43.203.176.193 "mkdir -p /home/ubuntu/app"
					    scp -i "$SSH_KEY" -o StrictHostKeyChecking=no docker-compose.yml ubuntu@43.203.176.193:/home/ubuntu/app/docker-compose.yml
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
					    ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ubuntu@43.203.176.193 \
					    "cd /home/ubuntu/app && \
					    docker-compose down && \
					    docker-compose pull && \
					    docker-compose up -d"
					   '''
				}
			}
		}
		
	}
	
} // pipeline 종료
post {
	success {
		echo '======================='
		echo 'Docket Compose 배포 성공!!'
		echo '======================='
	}
	failure {
		echo '======================='
		echo 'Docket Compose 배포 실패!!'
		echo '======================='
		sh '''
		    docker compose ps || true
		   '''
	}
}