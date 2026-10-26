import { HttpError } from '../utils/http.js';

export function notFound(req, res) {
  res.status(404).json({ error: `Không tìm thấy ${req.method} ${req.originalUrl}` });
}

// eslint-disable-next-line no-unused-vars
export function errorHandler(err, req, res, next) {
  if (err instanceof HttpError) {
    return res.status(err.status).json({ error: err.message, details: err.details });
  }
  if (err.type === 'entity.parse.failed') {
    return res.status(400).json({ error: 'Body không phải JSON hợp lệ' });
  }
  console.error(err);
  res.status(500).json({ error: 'Lỗi máy chủ' });
}
