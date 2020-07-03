export interface Transaction {
    transaction_id?: number;
    created?: Date;
    value: number;
    accoount_from: number;
    accoount_to: number;
}