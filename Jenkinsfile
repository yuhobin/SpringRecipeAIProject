pipeline {
	/*
		소기업 : Git Actions
		중소기업 : Jenkins
		대기업 : 자체처리
			= docker, docker-compose
		전체 동작 : Jenkins = 관리자
		Git Push
			|------ workflows(Git)
			|------ WebHook(트리거)
		Jenkins
			|------Permission 방지
					chmod +x geadlew
		Gradle Build
			|------ ./gradlew clean build -x test test제외 jar
		Docker Build
			|------ image만든다 docker build -t image명
		Docker Hub Push docker push image 명
			|------ 서버 종료
		Docker compose down
			|
		Docker compose Pull
			|
		Docker compose up -d
	*/
	agent any
	enviroment {
		APP_DIR = "~/app"
		JAR_NAME = "SpringRecipeAIProject-0.0.1-SNAPSHOT.jar"
		DOCKER_IMAGE = "yuhobin/ai-app:latest"
	}
	// 우분투 (AWS) 명령어 수행
	/*
		scm
			= git-url
			= Jenkinsfile 인식
	*/
	stages {
		// 1. Git Checkout : Repository 확인
		stage("Repository Checkout") {
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		// yml 인식 => ${POST_URL}, api-key : ${GEN_KEY}
		stage ("Create .env") {
			steps {
				withCredentials([
					string(
						credentialsId: 'post-url',
						variable: 'POST_URL'
					),
					string(
						credentialsId: 'gen-key',
						variable: 'GEN_KEY'
					)
				]){
					sh '''
						echo "SPRING_PROFILES_ACTIVE=prod" > .env
						echo "POST_URL=${POST_URL}" >> .env
						echo "GEN_KEY=${GEN_KEY}" >> .env
						
						chmod 600 .env
						'''
				}
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
		
		// 4. gradlew build  => 배포 파일 만들기 (jar파일 만들기)
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
		
		// 7. DockerHub Push
		stage("DockerHub Push") {
			steps {
				sh '''
					docker push ${DOCKER_IMAGE}
					'''
			}
		}
		
		// 8. 기존의 Container 종류 = ai-app
		stage("Docker Compose DOWN") {
			steps {
				sh '''
					docker compose down || true 
					'''
			}
		}
		
		// 9. 최신 이미지를 읽어 온다 
		stage("Docker Compose Pull") {
			steps {
				sh '''
					docker compose pull
					'''
			}
		}
		
		// 10. docker compose 실행
		stage("Docker Compose Up") {
			steps{
				sh '''
					docker compose up -d
					'''
			}
		}
		
		// 11. Container Check
		stage("Container Check") {
			steps {
				sh '''
					docker compose ps
					'''
			}
		}
	}
	
} // pipeline 종료
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
		sh '''
			docker compose ps || true
			'''
	}
}