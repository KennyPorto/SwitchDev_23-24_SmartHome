import {format} from "date-fns";

export const getCurrentDate = () => format(new Date(), 'EEE, dd MMMM')
