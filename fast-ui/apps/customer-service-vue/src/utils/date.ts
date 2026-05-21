import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'

dayjs.extend(relativeTime)

export const formatDate = (date: string | number | Date, format = 'YYYY-MM-DD HH:mm:ss') => {
  return dayjs(date).format(format)
}

export const fromNow = (date: string | number | Date) => {
  return dayjs(date).fromNow()
}

export const isSameDay = (date1: string | number | Date, date2: string | number | Date) => {
  return dayjs(date1).isSame(date2, 'day')
}

export const addDays = (date: string | number | Date, days: number) => {
  return dayjs(date).add(days, 'day').toDate()
}

export const subtractDays = (date: string | number | Date, days: number) => {
  return dayjs(date).subtract(days, 'day').toDate()
}
