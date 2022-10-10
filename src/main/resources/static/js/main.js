function getCourse(courseId) {
    fetch('http://localhost:8001/api/courses/' + courseId)
        .then(response => response.json())
        .then(data => {
                console.log(data);
                document.getElementById('editCourseId').value = courseId;
                document.getElementById('editCourseName').value = data.name;
                document.getElementById('editCourseDescription').value = data.description;
                document.getElementById('editCoursePrice').value = data.price;
            }
        )
}

async function updateCourse() {
    try {
        const response = await fetch('http://localhost:8001/api/courses', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: document.getElementById('editCourseId').value,
                name: document.getElementById('editCourseName').value,
                description: document.getElementById('editCourseDescription').value,
                price: document.getElementById('editCoursePrice').value
            })
        })
        document.getElementById('editCourseCloseBtn').click();
        document.getElementById('editCourseAlertSuccess').style.visibility = 'visible'
    } catch (error) {
        document.getElementById('editCourseAlert').style.visibility = 'visible';
    } finally {

    }
}