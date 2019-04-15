<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <div class="upload-file">

            <v-stepper v-model="e6" vertical>
                <v-stepper-step :complete="e6 > 1" step="1">
                    Выберете файл
                    <small>Файл в формате .xls</small>
                </v-stepper-step>

                <v-stepper-content step="1">
                    <v-btn color="primary" @click.native="openFileDialog">
                        Выбрать файл
                        <v-icon right dark> cloud_upload</v-icon>
                    </v-btn>
                    <input type="file" id="file-upload" style="display:none" @change="onFileChange">
                    <v-btn flat>Отмена</v-btn>
                </v-stepper-content>

                <v-stepper-step :complete="e6 > 2" step="2">Нажмите продолжить для загрузки файла</v-stepper-step>

                <v-stepper-content step="2">
                    <v-btn color="primary" @click="uploadFile">Загрузить</v-btn>
                    <v-btn flat>Отмена</v-btn>
                </v-stepper-content>

                <v-stepper-step :complete="e6 > 3" step="3">Нажмите продолжить для отображения таблицы торгов</v-stepper-step>

                <v-stepper-content step="3">
                    <v-btn color="primary" @click="getNomenclatures">Продолжить</v-btn>
                    <v-btn flat>Отмена</v-btn>
                </v-stepper-content>
            </v-stepper>


            <v-data-table
                    :headers="headers"
                    :items="nomenclatures"
                    class="elevation-1"
            >
                <template v-slot:no-data>
                    <v-alert :value="true" color="error" icon="warning">
                        Нет данных. Загрузите торги
                    </v-alert>
                </template>

                <template v-slot:items="props">
                    <td class="text-xs-center">{{ props.item.name}}</td>
                 <!--   <td>{{ props.item.mmn}}</td> -->
                    <td class="text-xs-center">{{ props.item.manufacture }}</td>
                    <td class="text-xs-center">{{ props.item.vital }}</td>
                    <td class="text-xs-center">{{ props.item.cost_vital }}</td>
                    <td class="text-xs-center">{{ props.item.quantity }}</td>
                    <td class="text-xs-center">{{ props.item.cost }}</td>
                    <td class="text-xs-center">{{ props.item.expiration }}</td>
                    <td class="text-xs-center">{{ props.item.prorogation }}</td>
                </template>

            </v-data-table>

    </div>



</template>

<script>
    import axios from 'axios'

    export default {
        name: "CustomBidding",
        data() {
            return {
                formData: new FormData(),
                e6: 1,
                headers: [
                    { text: 'Наименование', value: 'name' },
               //     { text: 'МНН', value: 'mmn' },
                    { text: 'Производитель', value: 'manufacture' },
                    { text: 'ЖВ', value: 'vital'},
                    { text: 'Реестр', value: 'cost_vital' },
                    { text: 'Кол-во', value: 'quantity' },
                    { text: 'Стартовая цена', value: 'cost' },
                    { text: 'СГ', value: 'expiration', sortable: false},
                    { text: 'Отсрочка платежа (дн.)', value: 'prorogation' },
                ],
                nomenclatures: []
            }
        },
        methods: {
            openFileDialog() {
                this.e6 = 2;
                document.getElementById('file-upload').click();
            },
            onFileChange(e) {
                var files = e.target.files || e.dataTransfer.files;
                if(files.length > 0){
                    for( var i = 0; i< files.length; i++){
                        this.formData.append("file", files[i], files[i].name);
                    }
                }
            },
            uploadFile() {
                this.e6 = 3;
                axios.post('/admin/setting/bidding?' + document.cookie, this.formData).then(function (response) {
                    console.log(response);
                    this.snackbar = true
                }).catch(function (error) {
                    console.log(error);
                });
            },
            getNomenclatures() {
                this.e6 = 4;
                axios({
                    method: 'get',
                    url: '/admin/setting/bidding?' + document.cookie,
                    headers: { 'Content-type' : 'application/json' },
                    data: {}
                }).then( response => {
                    this.nomenclatures = response.data
                });
            }
        },
        created() {
            axios({
                method: 'get',
                url: '/admin/setting/bidding?' + document.cookie,
                headers: { 'Content-type' : 'application/json' },
                data: {}
            }).then( response => {
                this.nomenclatures = response.data
            });
        }

    }
</script>

<style scoped>

</style>