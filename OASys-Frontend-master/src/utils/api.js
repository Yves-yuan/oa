import {get, post,download} from "./http";

export function login(data) {
    return post("/api/login", data);
}

export function logout() {
    return get("/api/logout");
}

export function getAuth() {
    return get("/api/auth");
}

export function getAttendances() {
    return get("/api/getAttendances");
}

export function getAttendance() {
    return get("/api/getAttendance");
}

export function signIn() {
    return get("/api/signIn");
}

export function signOut() {
    return get("/api/signOut");
}

export function getAttendanceTime() {
    return get("/api/getAttendanceTime");
}

export function setAttendanceTime(params) {
    return get("/api/setAttendanceTime", params);
}

export function getFiles(params) {
    return get("/api/getFiles", params);
}

export function addFolder(params) {
    return get("/api/addFolder", params);
}

export function renameFile(params) {
    return get("/api/renameFile", params);
}

export function deleteFile(data) {
    return post("/api/deleteFile", data);
}

export function askLeave(data) {
    return post("/api/askLeave", data);
}

export function checkLeave(data) {
    return post("/api/checkLeave", data);
}

export function getLeaves(params) {
    return get("/api/getLeaves", params);
}

export function getNotices() {
    return get("/api/getNotices");
}

export function markRead(data) {
    return post("/api/markRead", data);
}

export function getUsers(params) {
    return get("/api/getUsers", params);
}

export function getRoles() {
    return get("/api/getRoles");
}

export function addUser(data) {
    return post("/api/addUser", data);
}

export function updateUser(data) {
    return post("/api/updateUser", data);
}

export function deleteUser(data) {
    return post("/api/deleteUser", data);
}

export function addGoods(params) {
    return post("/api/addGoods", params);
}
export function getGoods(params) {
    return get("/api/getGoods", params);
}
export function getWarningGoods(params) {
    return get("/api/getWarningGoods", params);
}
export function exportGoods(params,filename) {
    return download("post","/api/exportGoods", params,filename);
}
export function deleteAllGoods(data) {
    return post("/api/deleteAllGoods", data);
}
export function deleteGoods(data) {
    return post("/api/deleteGoods", data);
}
export function updateGoods(data) {
    return post("/api/updateGoods", data);
}
// goods order

export function updateGoodsOrder(data) {
    return post("/goodsOrdering/update", data);
}
export function addOrdering(data){
    return post("/goodsOrdering/addOrdering", data);
}
export function getGoodsOrder(params) {
    return get("/goodsOrdering/getGoodsOrdering", params);
}

export function exportGoodsOrder(params,filename) {
    return download("post","/goodsOrdering/exportGoodsOrdering", params,filename);
}

export function deleteGoodsOrder(data) {
    return post("/goodsOrdering/delete", data);
}
export function deleteAllGoodsOrder(data) {
    return post("/goodsOrdering/deleteAll", data);
}


export function addFlight(params) {
    return post("/api/addFlight", params);
}
export function getFlight(params) {
    return get("/api/getFlight", params);
}
export function exportFlight(params,filename) {
    return download("post","/api/exportFlight", params,filename);
}

export function updateFlight(data) {
    return post("/api/updateFlight", data);
}

export function deleteFlight(data) {
    return post("/api/deleteFlight", data);
}
