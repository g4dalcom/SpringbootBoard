let temp;

$(document).ready(function () {
    showAllBoard(0);
})

function showAllBoard(page) {
    $('#one-board').hide()
    $('#update-board').hide()
    $('#main-boards').show()

    $('#tableTbody').empty()
    $.ajax({
        type: 'GET',
        url: '/api/boards/',
        data: {"page": page},
        success: function (response) {
            console.log(response)
            let boards = response.content;
            for (let i = 0; i < boards.length; i++) {
                let board = boards[i];
                let tempHtml = addHtml(board);
                $('#tableTbody').append(tempHtml);
            }

            $('#pageing-num').empty();
            let previous = response.number;
            let pageNumber = response.pageable.pageNumber;
            let pageSize = response.pageable.pageSize;
            let totalPages = response.totalPages;
            let startPage = Math.floor(pageNumber / pageSize) * pageSize + 1;
            let tempEndPage = startPage + pageSize - 1
            let endPage = tempEndPage > totalPages ? totalPages : tempEndPage;

            let temp
            if (!response.first) {
                temp = `<li class="page-item">
                            <a class="page-link" onclick='showAllBoard(${previous-1})' aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>`
            }

            $('#pageing-num').append(temp);
            console.log(startPage)
            console.log(endPage)
            for (let i = startPage; i <= endPage; i++) {
                temp = `<li class="page-item">
                            <a class="page-link" onclick="showAllBoard(${i-1})">${i}</a>
                        </li>`
                $('#pageing-num').append(temp);
            }

            if (!response.last) {
                temp = `<li class="page-item">
                            <a class="page-link" onclick="showAllBoard(${previous+1})" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>`
            }
            $('#pageing-num').append(temp);
        }
    })
}

function addHtml(board) {
    return `<tr>
              <th>${board.id}</th>
              <th onclick="showBoard(${board.id})" style="cursor:pointer">${board.title}</th>
              <th>${board.writer}</th>
              <th>${transTime(board.modifiedAt)}</th>
           </tr>`
}

function transTime(x) {
    let date = new Date(x);
    let month = date.getMonth()+1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();
    let second = date.getSeconds();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;
    hour = hour >= 10 ? hour : '0' + hour;
    minute = minute >= 10 ? minute : '0' + minute;
    second = second >= 10 ? second : '0' + second;
    return date.getFullYear() + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}

function showBoard(id) {
    $('#one-board').show()
    $('#main-boards').hide()
    $('#update-board').hide()
    $('#board').empty()
    temp = id;

    $.ajax({
        type: 'GET',
        url: '/api/board/' + id,
        success: function (response) {
            $('#board').append(addBoardHtml(response));
        }
    })
}

function addBoardHtml(board) {
        return `<div id="pre-update-board">
                <div class="mb-3 row">
                    <label class="col-sm-2 col-form-label">제목</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="pre-update-title" value="${board.title}" disabled readonly>
                    </div>
                </div>
                <div class="mb-3 row">
                    <label class="col-sm-2 col-form-label">작성자</label>
                    <div class="col-sm-10">
                        <input class="form-control" id="pre-update-write" type="text" value="${board.writer}" disabled readonly> <!--value="Disabled readonly input" disabled
                       readonly> -->
                    </div>
                </div>
                <div class="mb-3 row">
                    <label class="col-sm-2 col-form-label">비밀번호</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="pre-update-password" placeholder="글을 삭제하려면 이전에 입력한 비밀번호를 입력해주세요!">
                    </div>
                </div>
                <div class="mb-3">
                    <textarea class="form-control" id="pre-update-contents" rows="10" disabled readonly>${board.contents}</textarea>
                </div>
                <div id="one-board-btn">
                    <button type="button" class="btn btn-primary" onclick="showUpdateBoard()">수정</button>
                    <button type="button" class="btn btn-primary" onclick="deleteBoard()">삭제</button>
                    <button type="button" class="btn btn-primary" onclick="showAllBoard(0)">목록</button>
                </div>
            </div>`
}

function boardWrite() {
    let title = $('#board-title').val().trim();
    let write = $('#board-write').val().trim();
    let contents = $('#board-contents').val().trim();
    let password = $('#board-pw').val().trim();

    let data = {
        'title' : title,
        'writer' : write,
        'contents' : contents,
        'password' : password
    }

    $.ajax({
        type: 'POST',
        url: '/api/board',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('작성 완료');
            window.location.href = '/'
        }
    })
}

function showUpdateBoard() {
    $('#one-board').hide()
    $('#main-boards').hide()
    $('#update-board').show()

    $.ajax({
        type: 'GET',
        url: '/api/board/' + temp,
        success: function (response) {
            $('#update-title').val(`${response.title}`)
            $('#update-write').val(`${response.writer}`)
            $('#update-contents').val(`${response.contents}`)
        }
    })
}

function updateBoard() {
    let title = $('#update-title').val().trim();
    let writer = $('#update-write').val().trim();
    let contents = $('#update-contents').val().trim();
    let password = $('#update-password').val().trim();

    let data = {
        'title' : title,
        'writer' : writer,
        'contents' : contents,
        'password' : password
    }

    $.ajax({
        type: 'PUT',
        url: '/api/board/' + temp,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if(response == 0) {
                alert('비밀번호가 일치하지 않습니다');
            } else {
                alert('수정 완료');
                window.location.href = '/'
            }
        }
    })
}

function deleteBoard(id) {
    let password = $('#pre-update-password').val().trim();
    let data = {'password' : password};

    $.ajax({
        type: "DELETE",
        url: '/api/board/' + temp,        // controller 로 보내고자 하는 url
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if(response == 0) {
                alert('비밀번호가 일치하지 않습니다.');
            } else {
                alert('삭제 완료');
                window.location.href = '/'
            }
        }
    })
}
