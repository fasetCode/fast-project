import axios from '../../utils/axios';

/**
 * 获取慢查询日志列表
 * @param params 
 * @returns 
 */
export const getSlowQueryLogPage = (params: any) => {
  return axios.post('/api/system/slow-query', params);
};

/**
 * 批量删除慢查询日志
 * @param ids 
 * @returns 
 */
export const deleteSlowQueryLog = (ids: number[]) => {
  return axios.delete('/api/system/slow-query', { data: ids });
};

/**
 * 更新慢查询日志
 * @param data 
 * @returns 
 */
export const updateSlowQueryLog = (data: any) => {
  return axios.put('/api/system/slow-query', data);
};
