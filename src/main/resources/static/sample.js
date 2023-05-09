function board(boardId) {
    var board;
    $.ajax({
        type: 'GET',
        url: `/board/selectBoardById/${facilityId}`,
        dataType: 'JSON',
        contentType: 'application/json',
        async: false // 동기식으로 리턴 데이터 보장
    }).done(function(res) {
        if (res.code == "200") {
            facility = res.data;
        } else {
            alert(res.message);
        }
    }).fail(function (status, error) {
        alert(JSON.stringify(error))
    });

    return facility;
}

function findFacilityDetail(facilityId) {
    var facility;
    $.ajax({
        type: 'GET',
        url: `/api/facility/detail/${facilityId}`,
        dataType: 'JSON',
        contentType: 'application/json',
        async: false // 동기식으로 리턴 데이터 보장
    }).done(function(res) {
        if (res.code == "200") {
            facility = res.data;
        } else {
            alert(res.message);
        }
    }).fail(function (status, error) {
        alert(JSON.stringify(error))
    });

    return facility;
}

function findAllFacility() {
    var facilityList;
    $.ajax({
        type: 'GET',
        url: '/api/facility',
        dataType: 'JSON',
        contentType: 'application/json',
        async: true, // 동기식으로 리턴 데이터 보장,
        beforeSend : function (){
            Common.fixToast("시설물 데이터를 불러오고 있습니다.");
        }
    }).done(function(res) {
        if (res.code == "200") {
            facilityList = res.data;
            facilityList.forEach((marker) => Marker.markerMap[marker.type] ?
                Marker.markerMap[marker.type].push(marker) : Marker.markerMap[marker.type] = [marker]);
            // 맵 시설물/선로 표출
            MapLabel.getLabelSetting();
        } else {
            alert(res.message);
        }
        Common.removeToast();
        Common.toast("시설물 데이터가 맵에 표출되었습니다.");
    }).fail(function (status, error) {
        alert(JSON.stringify(error))
        Common.removeToast();
        Common.toast("시설물 데이터 조회에 실패하였습니다.");
    });

    return facilityList;
}

function resetFacilityInput(){
    Marker.deleteLayer(LAYER.TEMP);
    selectInputType = undefined;
}

function saveFacility(facilityDto) {

    var saveRes = false;

    if (!validateFacility(facilityDto)) return saveRes;

    Marker.deleteLayer(LAYER.TEMP);

    $.ajax({
        type: 'POST',
        url: '/api/facility',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(facilityDto),
        async: false
    }).done(function(res) {
        if (res.code == "200") {
            toast(MESSAGE.FACILITY.SAVE);
            facilityDto.facilityId = res.data;
            saveRes = true;
        } else {
            alert(res.message);
        }

    }).fail(function (status, error) {
        Common.alertError(status, error);
    });

    return saveRes;
}




function saveAddFacility(addFacilityDto) {

    var saveRes = false;

    if (!validateFacility(addFacilityDto)) return saveRes;

    $.ajax({
        type: 'POST',
        url: `/api/addFacility`,
        dataType: 'json',
        contentType: 'application/json',
        traditional: true,
        data: JSON.stringify(addFacilityDto),
        async: false
    }).done(function(res) {
        if (res.code == "200") {
            toast(res.message);

            saveRes = true;
        } else {
            alert(res.message);
        }

    }).fail(function (status, error) {
        Common.alertError(status, error);
    });

    return saveRes;
}

function deleteFacility(facilityId) {
    var saveRes = false;
    if(!confirm("삭제하시겠습니까?")){
        return saveRes;
    };

    $.ajax({
        type: 'DELETE',
        url: '/api/facility/'+facilityId,
        dataType: 'json',
        contentType: 'application/json',
        async: false
    }).done(function(res) {
        // console.log('res', res);
        if (res.code == 200) {
            if(res.data == "N"){
                toast("시설물을 정상적으로 삭제했습니다.")
                saveRes = true;
            }else {
                toast("선로에 연결되어 있는 시설물입니다.");
            }
        }else {
            alert(res.message);
        }
    }).fail(function (status, error) {
        Common.alertError(status, error);
        // alert(JSON.stringify(error))
    });
    return saveRes;
}

function getFacilityNextId() {
    var facilityId;
    $.ajax({
        type: 'GET',
        url: '/api/facility/nextId',
        dataType: 'JSON',
        contentType: 'application/json',
        async: false // 동기식으로 리턴 데이터 보장
    }).done(function(res) {
        if (res.code == "200") {
            facilityId = res.data;
        } else {
            alert(res.message);
        }
    }).fail(function (status, error) {
        alert(JSON.stringify(error))
    });

    return facilityId;
}

function updateFacility(facilityDto) {

    var saveRes = false;

    if (!validateFacility(facilityDto)) return saveRes;

    Marker.deleteLayer(LAYER.TEMP);

    $.ajax({
        type: 'PUT',
        url: '/api/facility',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(facilityDto),
        async: false
    }).done(function(res) {
        if (res.code == "200") {
            toast(MESSAGE.FACILITY.UPDATE);

            saveRes = true;
        } else {
            alert(res.message);
        }

    }).fail(function (status, error) {
        Common.alertError(status, error);
    });

    return saveRes;
}

function updateFacilityLocate(facilityDto) {

    var saveRes = false;

    $.ajax({
        type: 'PUT',
        url: '/api/facility',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(facilityDto),
        async: false
    }).done(function(res) {
        if (res.code == "200") {
            toast(MESSAGE.FACILITY.UPDATE);

            saveRes = true;
        } else {
            alert(res.message);
        }

    }).fail(function (status, error) {
        Common.alertError(status, error);
    });

    return saveRes;
}


//////////////////////////////////////////// 기타 함수 /////////////////////////////////////////////////////

/**
 * 시설물 필수 필드 유효성 체크
 * @param facilityDto 입력한 시설물 정보
 * @returns {boolean}
 */
function validateFacility(facilityDto) {

    var res = true;
    var failFieldName = '';

    // 입력한 시설물 타입에 정의된 필수필드 목록 (상세정보 > 기본정보 내의 필수필드)
    var fieldList = Policy.getRequiredFieldListByObjectCodeAndTabAreaCode(facilityDto.type, 'info');

    // 필수 필드중 미입력 필드 체크
    fieldList.every(function(f) {

        if (f.code == 'name') {
            res = facilityDto.facilityNm != undefined && facilityDto.facilityNm != '';
        } else {
            res = facilityDto[f.code] != undefined && facilityDto[f.code] != '';
        }

        if (!res) {
            failFieldName = Policy.getFieldRename(f.code, facilityDto.type);
            return false;
        }

        return true;
    });

    if (res) {
        return true;
    } else {
        var message = MESSAGE.FACILITY.VALIDATE.replaceAll('{field}', failFieldName);
        alert(message);
        return false;
    }
}


/**
 * 선로 타입별 목록(map) 조회
 */

function findFacilityGroupByType(group) {

    var key = group ? group : 'type';
    var facilityList = findAllFacility();

    return facilityList.reduce(function(rv, x) {
        (rv[x[key]] = rv[x[key]] || []).push(x);
        return rv;
    }, {});
}