pipeline {
    agent any
    environment {
        MAVEN_HOME = '/usr/bin'
        JIRA_URL = 'https://your-jira-instance.atlassian.net'
        ZEPHYR_API_TOKEN = credentials('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjb250ZXh0Ijp7ImJhc2VVcmwiOiJodHRwczovL2RlZXZ5YW5nMjEuYXRsYXNzaWFuLm5ldCIsInVzZXIiOnsiYWNjb3VudElkIjoiNzEyMDIwOmMxODA3ZDM2LWQ4M2ItNGM3NS04YjlkLWNhOWY0NTBiOWE4NSIsInRva2VuSWQiOiJkNjJmMzI3MS05OTVkLTQ4MzItOWU2MC04YWZkMTE5YzcxYWYifX0sImlzcyI6ImNvbS5rYW5vYWgudGVzdC1tYW5hZ2VyIiwic3ViIjoiNzEzY2RmM2YtNThlNS0zYzNhLTgyN2UtOTEwOGU4NTU4ZmRhIiwiZXhwIjoxNzYxMTk2NzE0LCJpYXQiOjE3Mjk2NjA3MTR9.kgMqrjPmaweQ6u_MG6PJM9IfK-BBLi4cuzmKlWPxdr8')
        ZEPHYR_PROJECT_KEY = 'JEN'
        ZEPHYR_VERSION_ID = '-1' // Replace with appropriate version ID
    }
    stages {
        stage('Build') {
            steps {
                // Running Maven tests
                sh "${MAVEN_HOME}/mvn clean test"
            }
        }
        stage('Generate Report') {
            steps {
                script {
                    // Assuming extent report is in the test-output folder
                    archiveArtifacts artifacts: 'test-output/*.html', allowEmptyArchive: true
                }
            }
        }
        stage('Publish Results to Zephyr') {
            steps {
                script {
                    // Use CURL to push the test results to Zephyr Scale via API
                    def reportFilePath = 'test-output/extent.html' // Updated path
                    def curlCommand = """
                    curl -X POST "${JIRA_URL}/rest/zapi/latest/execution" \
                    -H "Authorization: Bearer ${ZEPHYR_API_TOKEN}" \
                    -H "Content-Type: application/json" \
                    -d '{
                        "projectKey": "${ZEPHYR_PROJECT_KEY}",
                        "versionId": "${ZEPHYR_VERSION_ID}",
                        "cycleName": "Regression Cycle",
                        "folderName": "Test Automation",
                        "status": "Pass",
                        "comment": "Selenium Maven Extent Report Test Execution",
                        "executionFiles": "${reportFilePath}"
                    }'
                    """
                    sh curlCommand
                }
            }
        }
    }
    post {
        always {
            // Always clean up the workspace after build
            cleanWs()
        }
    }
}
