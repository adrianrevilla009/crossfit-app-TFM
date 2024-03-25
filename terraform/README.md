This terraform infrastructure is based on [Harshetjain666's infrastructure](https://github.com/Harshetjain666/terraform-aws-eks-fargate-cluster), but has been modified to fit this project's needs.

## Deploying

Terraform requires to first initialize the local workspace, which will download the required providers and modules, and synchronize the state with the remote backend. This is done by running the following command:

```bash
terraform init
```

> **Note**: As you can see in the [`main.tf`](main.tf) file, this will use a profile named `crossfitapp` in the AWS credentials file. If you want to use a different profile, you can change it in the `main.tf` file. This means that you will need to have a profile with the name `tastr` in your AWS credentials file, with the required permissions to create the resources. If you don't have these credentials, you can contact AWS's TASTR accoount's admin to get them.

Once the workspace is initialized, you can deploy the infrastructure by running the following command:

```bash
terraform apply -var-file=default.tfvars
```

This should prompt for the database password, and once that is provided, it will start checking against the backend for changes in the infrastructure. The changes will be shown in the console, and you will be prompted to confirm the changes. Enter `yes` to confirm the changes, and Terraform will start creating the resources.

## Destroying

To destroy the infrastructure, you can run the following command:

```bash
terraform destroy -var-file=default.tfvars
```

Destroying can take a while, and it may output a timeout if some conditions are not met. Something that can make the command timeout is the `argocd` namespace not being deleted, and getting stuck in the `Terminating` state. If this happens, you can delete the namespace manually by running the following command:

```bash
kubectl get Application -A -o name | xargs kubectl patch -p '{"metadata":{"finalizers":null}}' --type=merge -n argocd
```

This should remove all the orphan ArgoCD applications, which in turn will allow the namespace to be deleted. Once the namespace is deleted, you can run the `terraform destroy` command again, and it should finish successfully.