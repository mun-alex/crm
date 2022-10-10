function getCourse(courseId) {
    fetch('http://localhost:8001/api/courses/' + courseId)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            document.getElementById('editCourseName').value = data.name;
            document.getElementById('editCourseDescription').value = data.description;
            document.getElementById('editCoursePrice').value = data.price;
            }
        )
}

function saveCourse(courseId) {
    fetch('http://localhost:8001/api/courses' + courseId)

}