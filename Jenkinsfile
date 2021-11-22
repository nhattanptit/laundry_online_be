pipeline {
    agent {
        label 'master'
    }
    tools {
        maven '3.8.4'
        jdk 'jdk8'
    }
    stages {
        stage('Building our image') {
            steps {
                script {
//                    dockerImage = docker.build "zonesama/laundry-be:$BUILD_NUMBER"
//                    sh 'mvn -B -DskipTests clean package'
                    sh 'docker build -t zonesama/laundry-be .'
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
