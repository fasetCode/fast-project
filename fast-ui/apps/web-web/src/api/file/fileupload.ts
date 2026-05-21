/**
 * 根据文件ID构造文件访问URL（后端会302重定向到实际地址，浏览器<img>标签会自动跟随）
 */
export const getFileUrl = (fileId: number | string): string => {
    return `/api/file/getUrl/${fileId}`
}