<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
    <v-data-table
            :headers="headers"
            :items="nomenclatures"
            class="elevation-1"
    >
        <template v-slot:no-data>
            <v-alert :value="true" color="blue" icon="warning">
                Торги начнутся через ....
            </v-alert>
        </template>

        <template v-slot:items="props">
            <td class="text-xs-left">{{ props.item.name}}</td>
          <!--  <td class="text-xs-left">{{ props.item.mmn}}</td> -->
            <td class="text-xs-left">{{ props.item.manufacture }}</td>
            <td class="text-xs-left">{{ props.item.vital }}</td>
            <td class="text-xs-left">{{ props.item.cost_vital }}</td>
            <td class="text-xs-left">{{ props.item.quantity }}</td>
            <td class="text-xs-left">{{ props.item.cost }}</td>
            <td class="text-xs-left">{{ props.item.min_cost }}</td>
            <td class="text-xs-left">{{ props.item.expiration }}</td>
            <td class="text-xs-left">{{ props.item.prorogation }}</td>

            <td class="justify-center layout px-0">
                <v-icon
                        class="mr-2"
                        @click="add(props.item)"
                >
                    add
                </v-icon>
                <v-icon
                        class="mr-2"
                        @click="remove(props.item)"
                >
                    remove
                </v-icon>
            </td>

        </template>

    </v-data-table>
    </v-layout>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Bidding",
        data() {
            return {
                headers: [
                    { align: 'left', width: 10, text: 'Наименование', value: 'name'},
                    //   { align: 'left', width: 10, text: 'МНН', value: 'mmn' },
                    { align: 'left', width: 10, text: 'Производитель', value: 'manufacture' },
                    { align: 'left', width: 10, text: 'ЖВ', value: 'vital'},
                    { align: 'left', width: 10, text: 'Реестр', value: 'cost_vital' },
                    { align: 'left', width: 10, text: 'Кол-во', value: 'quantity' },
                    { align: 'left', width: 10, text: 'Стартовая цена', value: 'cost' },
                    { align: 'left', width: 10, text: 'Лучшее предложение', value: 'min_cost' },
                    { align: 'left', width: 10, text: 'СГ', value: 'expiration', sortable: false},
                    { align: 'left', width: 10, text: 'Отсрочка платежа (дн.)', value: 'prorogation' },
                    { text: 'Выбрать/Убрать', value: 'Action', sortable: false }
                ],
                nomenclatures: [],
                checks: []
            }
        },
        created() {
            axios({
                method: 'get',
                url: '/bidding/check?' + document.cookie,
                headers: { 'Content-type' : 'application/json' },
                data: {}
            }).then( response => {
                this.nomenclatures = response.data
            });

        },
        mounted() {

        },
        methods: {
            add(item) {
                axios({
                    method: 'put',
                    url: '/bidding/choose/' + item.id + '?' + document.cookie + '&target=add',
                    headers: { 'Content-type' : 'application/json' },
                    data: {}
                }).then( response => {
                    console.log(item)
                });
            },
            remove(item) {
                axios({
                    method: 'put',
                    url: '/bidding/choose/' + item.id + '?' + document.cookie + '&target=remove',
                    headers: { 'Content-type' : 'application/json' },
                    data: {}
                }).then(response => {
                    console.log(item);
                });
            }
        }
    }
</script>

<style scoped>

</style>