pipeline {
	agent any
	environment {
		APP_DIR="~/app"
		JAR_NAME="SpringRecipeAIProject-0.0.1-SNAPSHOT.jar"
	}
	stages {
		/*
			git push = commit (main)
				|
			web hook / poll
				|
			Jenkins (local) = EC2
				|
			  build
				|
			docker build
			docker push
				|
			docker pull
			docker run
		*/
		/*
			Repository : 소스파일 => Git URL
			
		*/
		stage('Check Out') {
			steps {
				echo 'Git Checkout'
				checkout scm
			}
		}
		// 임시 
		stage('Create .env') {
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
				]) {
					sh '''
						echo "SPRING_PROFILES_ACTICE=prod > .env
						echo "POST_URL=${POST_URL}" >> .env
						echo "GEN_KEY=${GEN_KEY}" >> .env
						
						chmod 600 .env
						'''
				}
			}
		}
		// gradlew build => permission 처리
		stage('Gradlew Permission'){
			steps {
				sh '''
					chmod +x gradlew
					'''
			}
		}
		
		// gradle build
		stage('Gradlew build') {
			steps {
				sh '''
					./gradlew clean build -x test
					'''
			}
		}
		// Dokcer Build
		stage('Docker build') {
			steps {
				sh '''
					docker build -t yuhobin/ai-app:latest .
					'''
			}
		}
		// DockerHub Login
		stage('Docker Login') {
			steps {
				withCredentials([usernamePassword(
					credentialsId:'dockerhub_info',
					usernameVariable:'DH_USER',
					passwordVariable:'DH_PASS'
				)]) {
					sh '''
						echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
						'''
				}
			}
		}
		
		stage('Docker Push') {
			steps {
				sh '''
					docker push yuhobin/ai-app:latest
					'''
			}
		}
		stage('Container Stop') {
			steps {
				sh '''
					docker stop ai-app || true
					'''
			}
		}
		stage('Container Remove') {
			steps {
				sh '''
					docker rm ai-app || true
					'''
			}
		}
		
		stage('DockerHub Pull') {
			steps {
				sh '''
					docker pull yuhobin/ai-app:latest
					'''
			}
		}
		stage('Docker Run') {
			steps {
				sh '''
					docker run -d --name ai-app -p 9090:9090 --env-file .env yuhobin/ai-app:latest
					'''
			}
		}
	}
}