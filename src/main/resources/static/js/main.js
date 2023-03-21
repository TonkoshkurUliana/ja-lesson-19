var singleUploadForm = document.getElementById('singleUploadForm');
var singleFileUploadInput = document.getElementById('file');
var singleNameUploadInput = document.getElementById('firstName');
var singleLastNameUploadInput = document.getElementById('lastName');
var singleAgeUploadInput = document.getElementById('age');
var singleFileUploadError = document.getElementById('singleFileUploadError');
var singleFileUploadSuccess = document.getElementById('singleFileUploadSuccess');

function uploadSingleFile(file) {
    let formData = new FormData();
    formData.append("firstName", singleNameUploadInput.value);
    formData.append("lastName", singleLastNameUploadInput.value);
    formData.append("age", singleAgeUploadInput.value);
    formData.append("file", file);
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "/register");

    xhr.onload = function() {
        console.log(xhr.responseText);
        let response = JSON.parse(xhr.responseText);
        if (xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='"
                + response.fileDownloadUri
                + "' target='_blank'>"
                + response.fileDownloadUri + "</a></p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message)
                || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

singleUploadForm.addEventListener('submit', function(event) {
    let files = singleFileUploadInput.files;
    if (files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

