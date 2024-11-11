import Http from '../http';

export const engines = function (engineName: any, pageNo:any, pageSize:any) {
    return Http.get('/api/v1/engine/list?name=' + engineName + "&pageNo=" + pageNo + "&pageSize=" + pageSize);
}

export const engineDetail = function (engineId: any) {
    return Http.get('/api/v1/engine/detail?id=' + engineId);
}

export const engineEdit = function (data: any) {
    return Http.post('/api/v1/engine/edit', data);
}

export const engineChangeStatus = function (id: any, status:any) {
    return Http.get('/api/v1/engine/changeStatus?id=' + id + "&status=" + status);
}