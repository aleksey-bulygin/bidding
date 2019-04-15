<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-layout>
        <v-data-table
                :headers="headers"
                :items="nomenclatures"
                class="elevation-1"
        >
            <template v-slot:no-data>
                <v-alert :value="true" color="blue" icon="warning">
                    Вы не выбрали ни одной позиции
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
                    <v-text-field
                            v-model="props.item.min_cost"
                            label="Предложение:"
                    ></v-text-field>

                    <v-icon
                            class="mr-2"
                            @click="push(props.item)"
                    >
                        add_box
                    </v-icon>
                </td>


            </template>

        </v-data-table>
    </v-layout>
</template>

<script>
    import axios from 'axios'

    export default {
        name: "Playing",
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
                    { align: 'left', width: 10, text: 'Предложить цену', value: 'newCost', sortable: false },
        //            { text: 'Отправить', value: 'Push', sortable: false }
                ],
                nomenclatures: [],
                costs:[]
            }
        },
        created() {
            axios({
                method: 'get',
                url: '/bidding/choose/play?' + document.cookie,
                headers: { 'Content-type' : 'application/json' },
                data: {}
            }).then( response => {
                this.nomenclatures = response.data
               for (let arg of this.nomenclatures) {
                   console.log(arg)
               }
            });


        },
        methods: {
            push(item) {
                axios({
                    method: 'put',
                    url: '/bidding/choose/play/' + item.id + '?' + document.cookie,
                    headers: { 'Content-type' : 'application/json' },
                    data : {
                        id : '',
                        cost : item.min_cost
                    }
                }).then( response => {
                   this.nomenclatures = response.data
                });
            }
        }
    }
</script>

<style scoped>

</style>