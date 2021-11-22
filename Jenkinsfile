pipeline {
    agent {
        label 'master'
    }
    stages {
        stage('Building our image') {
            steps {
                script {
//                    dockerImage = docker.build "zonesama/laundry-be:$BUILD_NUMBER"
                    sh './mvnw install'
                }
            }
        }
        stage('Deploy our image') {
            steps {
                script {
                    // Assume the Docker Hub registry by passing an empty string as the first parameter
//                    docker.withRegistry('' , 'dockerhub') {
//                        dockerImage.push()
//                    }
                    sh './mvnw test'
                }
            }
        }
    }
}
