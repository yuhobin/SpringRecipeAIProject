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
		
		// 5. Docker Image 생성
		stage("Docker Build") {
			steps {
				sh '''
				    docker build -t ${DOCKER_IMAGE} .
				   '''
			}
		}
		
		// 6. Docker Hub Login (파이프 | 추가)
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
		
		// 7. Dockerhub Push
		stage("DockerHub Push") {
			steps {
				sh '''
				    docker push ${DOCKER_IMAGE}
				   '''
			}
		}
		
		// 8. SSH KEY 설정
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
		
		// 9. AWS 접근 Known Hosts 등록
		stage("Known Hosts"){
			steps {
				sh '''
				    mkdir -p ~/.ssh
				    ssh-keyscan -H ${SERVER_IP} >> ~/.ssh/known_hosts
				    chmod 644 ~/.ssh/known_hosts
				   '''
			}
		}
		
		// 10. .env 생성 (따옴표 충돌 해결 및 변수 처리 통일)
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
					   ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} \
					   "mkdir -p ${APP_DIR} && \
					   cd ${APP_DIR} && \
					   rm -f .env && \
					   echo 'SPRING_PROFILES_ACTIVE=prod' > .env && \
					   echo 'POST_URL=${POST_URL}' >> .env && \
					   echo 'GEN_KEY=${GEN_KEY}' >> .env && \
					   chmod 600 .env"
					   '''
				}
			}
		}

        // 11. docker-compose.yml 이동 
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
		
		// 12. Deploy
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
					    ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no ${SERVER_USER}@${SERVER_IP} \
					    "cd ${APP_DIR} && \
					    docker-compose down || true && \
					    docker-compose pull && \
					    docker-compose up -d"
					   '''
				}
			}
		}
	} // stages 블록 종료
	
	// post 블록을 pipeline 내부로 이동 및 오타 수정
	post {
		success {
			echo '======================='
			echo 'Docker Compose 배포 성공!!'
			echo '======================='
		}
		failure {
			echo '======================='
			echo 'Docker Compose 배포 실패!!'
			echo '======================='
			sh '''
			    docker compose ps || true
			   '''
		}
	}
} // pipeline 블록 종료