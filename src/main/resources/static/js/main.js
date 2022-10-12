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
    let courseId = document.getElementById('editCourseId').value;
    let courseName = document.getElementById('editCourseName').value;
    let coursePrice = document.getElementById('editCoursePrice').value;
    try {
        const response = await fetch('http://localhost:8001/api/courses', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: courseId,
                name: courseName,
                description: document.getElementById('editCourseDescription').value,
                price: coursePrice
            })
        })
        document.getElementById('courseName' + courseId).innerText = courseName;
        document.getElementById('coursePrice' + courseId).innerText = coursePrice;
        document.getElementById('editCourseCloseBtn').click();
        document.getElementById('editCourseAlertSuccess').style.visibility = 'visible'
    } catch (error) {
        document.getElementById('editCourseAlert').style.visibility = 'visible';
    } finally {

    }
}

function getDepartment(departmentId) {
    fetch('http://localhost:8001/api/departments/' + departmentId)
        .then(response => response.json())
        .then(data => {
                console.log(data);
                document.getElementById('editDepartmentId').value = departmentId;
                document.getElementById('editDepartmentName').value = data.name;
            }
        )
}

async function updateDepartment() {
    let departmentId = document.getElementById('editDepartmentId').value;
    let departmentName = document.getElementById('editDepartmentName').value;
    try {
        const response = await fetch('http://localhost:8001/api/departments', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: departmentId,
                name: departmentName
            })
        })
        document.getElementById('departmentName' + departmentId).innerText = departmentName;
        document.getElementById('editDepartmentCloseBtn').click();
        document.getElementById('editDepartmentAlertSuccess').style.visibility = 'visible'
    } catch (error) {
        document.getElementById('editDepartment').style.visibility = 'visible';
    } finally {

    }
}