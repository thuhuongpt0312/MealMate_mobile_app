/** Lỗi có mã HTTP, ném ra trong route để errorHandler trả về JSON. Express 5 tự bắt lỗi của route async. */
export class HttpError extends Error {
  constructor(status, message, details) {
    super(message);
    this.status = status;
    this.details = details;
  }
}
