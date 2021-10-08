const convertLocalDateTimeToString = (localDateTime: string): string => { // yyyy-MM-dd`T`HH:mm:ss
  if (localDateTime === '' || localDateTime === undefined) {
    return '';
  }

  const tokens = localDateTime.split('T');

  const date = tokens[0];
  const time = tokens[1];

  const dateTokens = date.split('-')
  const timeTokens = time.split(':')

  return `${dateTokens[0]}년 ${dateTokens[1]}월 ${dateTokens[2]}일 ${timeTokens[0]}시 ${timeTokens[1]}분 ${timeTokens[2]}초`
}

export {
  convertLocalDateTimeToString
}
