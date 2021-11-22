pipeline {
    agent {
        label 'master'
    }
    tools {
        maven '3.8.4'
        jdk 'jdk8'
    }
    stages {
        stage('Building maven and run test') {
            steps {
                script {
                    sh 'mvn -B clean package'
                }
            }
        }
        stage('Building docker image'){
            steps{
                script{
                    sh 'docker build -t zonesama/laundry-be .'
                }
            }
        }
        stage('Pushing builded image to docker hub'){
            steps{
                script{
                    sh 'docker push "zonesama/laundry-be"'
                }
            }
        }
//        stage('Deploy our image') {
//            steps {
//                script {
//                    // Assume the Docker Hub registry by passing an empty string as the first parameter
////                    docker.withRegistry('' , 'dockerhub') {
////                        dockerImage.push()
////                    }
//                    sh './mvnw test'
//                }
//            }
//        }
    }
}
